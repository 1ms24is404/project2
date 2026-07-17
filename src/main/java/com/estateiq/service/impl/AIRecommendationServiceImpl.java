package com.estateiq.service.impl;

import com.estateiq.client.GeminiClient;
import com.estateiq.dto.RecommendationRequestDTO;
import com.estateiq.dto.RecommendationResponseDTO;
import com.estateiq.entity.Recommendation;
import com.estateiq.repository.RecommendationRepository;
import com.estateiq.service.AIRecommendationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AIRecommendationServiceImpl implements AIRecommendationService {

    private static final Logger log = LoggerFactory.getLogger(AIRecommendationServiceImpl.class);

    private final GeminiClient geminiClient;
    private final RecommendationRepository recommendationRepository;

    public AIRecommendationServiceImpl(GeminiClient geminiClient,
                                       RecommendationRepository recommendationRepository) {
        this.geminiClient = geminiClient;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    @Transactional
    public RecommendationResponseDTO generateRecommendation(RecommendationRequestDTO requestDTO) {
        log.info("Generating recommendation for location={} purpose={}", requestDTO.getLocation(), requestDTO.getPurpose());

        int affordabilityScore = calculateAffordabilityScore(requestDTO);
        int investmentScore = calculateInvestmentScore(requestDTO);
        String prompt = buildPrompt(requestDTO, affordabilityScore, investmentScore);

        String recommendationText = null;
        try {
            recommendationText = geminiClient.generateContent(prompt);
        } catch (Exception ex) {
            log.error("Gemini recommendation generation failed", ex);
        }

        RecommendationResponseDTO responseDTO = parseRecommendation(recommendationText, requestDTO, affordabilityScore, investmentScore);

        Recommendation recommendation = new Recommendation();
        recommendation.setUserId(null);
        recommendation.setPropertyId(null);
        recommendation.setRecommendationText(responseDTO.getFinalRecommendation());
        recommendation.setAffordabilityScore(affordabilityScore);
        recommendation.setInvestmentScore(investmentScore);
        recommendation.setCreatedAt(LocalDateTime.now());
        recommendationRepository.save(recommendation);

        return responseDTO;
    }

    private int calculateAffordabilityScore(RecommendationRequestDTO requestDTO) {
        double salaryToEmiRatio = requestDTO.getEmi() / requestDTO.getMonthlySalary();
        double budgetRatio = requestDTO.getPropertyPrice() / requestDTO.getBudget();

        int salaryComponent;
        if (salaryToEmiRatio <= 0.25) {
            salaryComponent = 60;
        } else if (salaryToEmiRatio <= 0.35) {
            salaryComponent = 45;
        } else if (salaryToEmiRatio <= 0.45) {
            salaryComponent = 30;
        } else {
            salaryComponent = 15;
        }

        int budgetComponent;
        if (budgetRatio <= 0.8) {
            budgetComponent = 40;
        } else if (budgetRatio <= 1.0) {
            budgetComponent = 25;
        } else if (budgetRatio <= 1.2) {
            budgetComponent = 10;
        } else {
            budgetComponent = 0;
        }

        return Math.min(100, salaryComponent + budgetComponent);
    }

    private int calculateInvestmentScore(RecommendationRequestDTO requestDTO) {
        Map<String, Integer> locationMap = new HashMap<>();
        locationMap.put("bangalore", 30);
        locationMap.put("pune", 26);
        locationMap.put("hyderabad", 28);
        locationMap.put("chennai", 24);
        locationMap.put("mumbai", 22);
        locationMap.put("delhi", 23);

        int locationScore = locationMap.getOrDefault(requestDTO.getLocation().toLowerCase(), 15);
        double priceToBudgetRatio = requestDTO.getPropertyPrice() / requestDTO.getBudget();

        int valueScore;
        if (priceToBudgetRatio <= 0.8) {
            valueScore = 70;
        } else if (priceToBudgetRatio <= 1.0) {
            valueScore = 55;
        } else if (priceToBudgetRatio <= 1.2) {
            valueScore = 35;
        } else {
            valueScore = 15;
        }

        return Math.min(100, locationScore + valueScore);
    }

    private String buildPrompt(RecommendationRequestDTO requestDTO,
                               int affordabilityScore,
                               int investmentScore) {
        return "You are a senior real-estate investment advisor in India. "
                + "Analyze this request for a property in " + requestDTO.getLocation() + ".\n"
                + "Context:\n"
                + "- Monthly salary: " + requestDTO.getMonthlySalary() + "\n"
                + "- Budget: " + requestDTO.getBudget() + "\n"
                + "- Property price: " + requestDTO.getPropertyPrice() + "\n"
                + "- Calculated EMI: " + requestDTO.getEmi() + "\n"
                + "- Purpose: " + requestDTO.getPurpose() + "\n"
                + "- Affordability score (calculated): " + affordabilityScore + "/100\n"
                + "- Investment score (calculated): " + investmentScore + "/100\n\n"
                + "Provide your analysis STRICTLY in JSON format matching this schema:\n"
                + "{\n"
                + "  \"propertySummary\": \"A short description summarizing the deal context.\",\n"
                + "  \"pros\": [\"pro 1\", \"pro 2\", ...],\n"
                + "  \"cons\": [\"con 1\", \"con 2\", ...],\n"
                + "  \"whyThisLocation\": \"Why this location fits the user's requirements.\",\n"
                + "  \"futureAppreciation\": \"Appreciation outlook for this sublocation/city.\",\n"
                + "  \"rentalDemand\": \"Rental demand and yield profile.\",\n"
                + "  \"nearbyInfrastructure\": \"Details on metro, highway, schools, or IT parks nearby.\",\n"
                + "  \"investmentRating\": \"A rating like 'Strong Buy', 'Hold', or 'Avoid'.\",\n"
                + "  \"shouldBuy\": \"Clear advice on whether the user should purchase this property.\",\n"
                + "  \"alternativeLocation\": \"An alternative locality or city that offers better value.\",\n"
                + "  \"finalRecommendation\": \"A concluding paragraph summarizing your professional advice.\"\n"
                + "}\n"
                + "Ensure it is valid JSON. Do not write markdown tags like ```json or any other text before or after the JSON.";
    }

    private RecommendationResponseDTO parseRecommendation(String jsonText, RecommendationRequestDTO requestDTO, int affordabilityScore, int investmentScore) {
        if (jsonText == null || jsonText.isBlank()) {
            return buildFallback(requestDTO, affordabilityScore, investmentScore);
        }

        String cleanJson = jsonText.trim();
        if (cleanJson.startsWith("```json")) {
            cleanJson = cleanJson.substring(7);
        } else if (cleanJson.startsWith("```")) {
            cleanJson = cleanJson.substring(3);
        }
        if (cleanJson.endsWith("```")) {
            cleanJson = cleanJson.substring(0, cleanJson.length() - 3);
        }
        cleanJson = cleanJson.trim();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(cleanJson, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});
            
            String propertySummary = (String) map.getOrDefault("propertySummary", "");
            List<String> pros = (List<String>) map.get("pros");
            List<String> cons = (List<String>) map.get("cons");
            String whyThisLocation = (String) map.getOrDefault("whyThisLocation", "");
            String futureAppreciation = (String) map.getOrDefault("futureAppreciation", "");
            String rentalDemand = (String) map.getOrDefault("rentalDemand", "");
            String nearbyInfrastructure = (String) map.getOrDefault("nearbyInfrastructure", "");
            String investmentRating = (String) map.getOrDefault("investmentRating", "");
            String shouldBuy = (String) map.getOrDefault("shouldBuy", "");
            String alternativeLocation = (String) map.getOrDefault("alternativeLocation", "");
            String finalRecommendation = (String) map.getOrDefault("finalRecommendation", "");

            return new RecommendationResponseDTO(
                finalRecommendation, affordabilityScore, investmentScore,
                propertySummary, pros, cons, whyThisLocation,
                futureAppreciation, rentalDemand, nearbyInfrastructure,
                investmentRating, shouldBuy, alternativeLocation, finalRecommendation
            );
        } catch (Exception e) {
            log.error("Failed to parse Gemini recommendation JSON, fallback initiated", e);
            return buildFallback(requestDTO, affordabilityScore, investmentScore);
        }
    }

    private RecommendationResponseDTO buildFallback(RecommendationRequestDTO requestDTO, int affordabilityScore, int investmentScore) {
        String summary = "Based on the provided salary of ₹" + requestDTO.getMonthlySalary() 
                + ", budget of ₹" + requestDTO.getBudget() 
                + ", and property price of ₹" + requestDTO.getPropertyPrice() 
                + ", this property represents a calculated choice for " + requestDTO.getPurpose() + ".";
        
        List<String> pros = new ArrayList<>();
        if (affordabilityScore >= 70) {
            pros.add("High affordability profile: Monthly EMI is well within your salary range.");
        } else {
            pros.add("EMI-to-salary ratio is manageable but requires close budget control.");
        }
        if (investmentScore >= 70) {
            pros.add("High growth potential location: " + requestDTO.getLocation() + " is a primary real estate hub.");
        }

        List<String> cons = new ArrayList<>();
        if (affordabilityScore < 50) {
            cons.add("High financial stress: EMI exceeds 45% of your monthly take-home salary.");
        }
        if (requestDTO.getPropertyPrice() > requestDTO.getBudget()) {
            cons.add("Budget Overrun: Property price exceeds your defined budget constraint.");
        }

        String locationAdvice = requestDTO.getLocation() + " is generally a high-performing real estate area with steady development.";
        String appreciation = "Expected historical CAGR in " + requestDTO.getLocation() + " ranges between 6% to 10% depending on the exact project.";
        String rental = "Estimated rental yield: 2.5% - 3.8% annually based on city averages.";
        String infra = "Area is connected by local roads/highway network; metro/transit plans are expanding.";
        String rating = (investmentScore >= 75) ? "Buy" : (investmentScore >= 50) ? "Hold" : "Avoid";
        String should = (affordabilityScore >= 60 && investmentScore >= 60) ? "Yes, financially viable and good upside." : "Hold off or search for cheaper properties.";
        String alt = "Consider outer suburbs of " + requestDTO.getLocation() + " for cheaper entry-level options.";
        String finalAdvice = "Overall, with an affordability score of " + affordabilityScore + "/100 and investment score of " + investmentScore + "/100, we recommend analyzing your down payment capability before proceeding.";

        return new RecommendationResponseDTO(
            finalAdvice, affordabilityScore, investmentScore,
            summary, pros, cons, locationAdvice,
            appreciation, rental, infra,
            rating, should, alt, finalAdvice
        );
    }
}
