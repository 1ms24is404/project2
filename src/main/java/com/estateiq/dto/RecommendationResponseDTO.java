package com.estateiq.dto;

public class RecommendationResponseDTO {

    private String recommendationText;
    private Integer affordabilityScore;
    private Integer investmentScore;

    public RecommendationResponseDTO() {
    }

    public RecommendationResponseDTO(String recommendationText, Integer affordabilityScore, Integer investmentScore) {
        this.recommendationText = recommendationText;
        this.affordabilityScore = affordabilityScore;
        this.investmentScore = investmentScore;
    }

    public String getRecommendationText() {
        return recommendationText;
    }

    public void setRecommendationText(String recommendationText) {
        this.recommendationText = recommendationText;
    }

    public Integer getAffordabilityScore() {
        return affordabilityScore;
    }

    public void setAffordabilityScore(Integer affordabilityScore) {
        this.affordabilityScore = affordabilityScore;
    }

    public Integer getInvestmentScore() {
        return investmentScore;
    }

    public void setInvestmentScore(Integer investmentScore) {
        this.investmentScore = investmentScore;
    }
}
