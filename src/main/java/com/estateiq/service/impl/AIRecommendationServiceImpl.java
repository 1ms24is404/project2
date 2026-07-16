package com.estateiq.service.impl;

import com.estateiq.client.GeminiClient;
import com.estateiq.dto.RecommendationRequestDTO;
import com.estateiq.dto.RecommendationResponseDTO;
import com.estateiq.entity.Recommendation;
import com.estateiq.repository.RecommendationRepository;
import com.estateiq.service.AIRecommendationService;
import java.time.LocalDateTime;
import java.util.HashMap;
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

        String recommendationText;
        try {
            recommendationText = geminiClient.generateContent(prompt);
            if (recommendationText == null || recommendationText.isBlank()) {
                recommendationText = buildFallbackRecommendation(requestDTO, affordabilityScore, investmentScore);
            }
        } catch (Exception ex) {
            log.error("Gemini recommendation generation failed", ex);
            recommendationText = buildFallbackRecommendation(requestDTO, affordabilityScore, investmentScore);
        }

        Recommendation recommendation = new Recommendation();
        recommendation.setUserId(null);
        recommendation.setPropertyId(null);
        recommendation.setRecommendationText(recommendationText);
        recommendation.setAffordabilityScore(affordabilityScore);
        recommendation.setInvestmentScore(investmentScore);
        recommendation.setCreatedAt(LocalDateTime.now());
        recommendationRepository.save(recommendation);

        return new RecommendationResponseDTO(recommendationText, affordabilityScore, investmentScore);
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
        return "You are a real-estate advisor. "
                + "Analyze this request and provide a concise recommendation. "
                + "Monthly salary: " + requestDTO.getMonthlySalary() + ", budget: " + requestDTO.getBudget()
                + ", property price: " + requestDTO.getPropertyPrice() + ", emi: " + requestDTO.getEmi()
                + ", purpose: " + requestDTO.getPurpose() + ", location: " + requestDTO.getLocation()
                + ", affordability score: " + affordabilityScore + ", investment score: " + investmentScore + ".";
    }

    private String buildFallbackRecommendation(RecommendationRequestDTO requestDTO,
                                               int affordabilityScore,
                                               int investmentScore) {
        return "Based on the provided salary, budget, EMI, and location, this property appears to be "
                + "a balanced choice for " + requestDTO.getPurpose() + ". Affordability score: " + affordabilityScore
                + ", investment score: " + investmentScore + ".";
    }
}
