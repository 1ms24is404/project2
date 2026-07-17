package com.estateiq.dto;

public class RecommendationResponseDTO {

    private String recommendationText;
    private Integer affordabilityScore;
    private Integer investmentScore;

    private String propertySummary;
    private java.util.List<String> pros;
    private java.util.List<String> cons;
    private String whyThisLocation;
    private String futureAppreciation;
    private String rentalDemand;
    private String nearbyInfrastructure;
    private String investmentRating;
    private String shouldBuy;
    private String alternativeLocation;
    private String finalRecommendation;

    public RecommendationResponseDTO() {
    }

    public RecommendationResponseDTO(String recommendationText, Integer affordabilityScore, Integer investmentScore) {
        this.recommendationText = recommendationText;
        this.affordabilityScore = affordabilityScore;
        this.investmentScore = investmentScore;
    }

    public RecommendationResponseDTO(String recommendationText, Integer affordabilityScore, Integer investmentScore, String propertySummary, java.util.List<String> pros, java.util.List<String> cons, String whyThisLocation, String futureAppreciation, String rentalDemand, String nearbyInfrastructure, String investmentRating, String shouldBuy, String alternativeLocation, String finalRecommendation) {
        this.recommendationText = recommendationText;
        this.affordabilityScore = affordabilityScore;
        this.investmentScore = investmentScore;
        this.propertySummary = propertySummary;
        this.pros = pros;
        this.cons = cons;
        this.whyThisLocation = whyThisLocation;
        this.futureAppreciation = futureAppreciation;
        this.rentalDemand = rentalDemand;
        this.nearbyInfrastructure = nearbyInfrastructure;
        this.investmentRating = investmentRating;
        this.shouldBuy = shouldBuy;
        this.alternativeLocation = alternativeLocation;
        this.finalRecommendation = finalRecommendation;
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

    public String getPropertySummary() {
        return propertySummary;
    }

    public void setPropertySummary(String propertySummary) {
        this.propertySummary = propertySummary;
    }

    public java.util.List<String> getPros() {
        return pros;
    }

    public void setPros(java.util.List<String> pros) {
        this.pros = pros;
    }

    public java.util.List<String> getCons() {
        return cons;
    }

    public void setCons(java.util.List<String> cons) {
        this.cons = cons;
    }

    public String getWhyThisLocation() {
        return whyThisLocation;
    }

    public void setWhyThisLocation(String whyThisLocation) {
        this.whyThisLocation = whyThisLocation;
    }

    public String getFutureAppreciation() {
        return futureAppreciation;
    }

    public void setFutureAppreciation(String futureAppreciation) {
        this.futureAppreciation = futureAppreciation;
    }

    public String getRentalDemand() {
        return rentalDemand;
    }

    public void setRentalDemand(String rentalDemand) {
        this.rentalDemand = rentalDemand;
    }

    public String getNearbyInfrastructure() {
        return nearbyInfrastructure;
    }

    public void setNearbyInfrastructure(String nearbyInfrastructure) {
        this.nearbyInfrastructure = nearbyInfrastructure;
    }

    public String getInvestmentRating() {
        return investmentRating;
    }

    public void setInvestmentRating(String investmentRating) {
        this.investmentRating = investmentRating;
    }

    public String getShouldBuy() {
        return shouldBuy;
    }

    public void setShouldBuy(String shouldBuy) {
        this.shouldBuy = shouldBuy;
    }

    public String getAlternativeLocation() {
        return alternativeLocation;
    }

    public void setAlternativeLocation(String alternativeLocation) {
        this.alternativeLocation = alternativeLocation;
    }

    public String getFinalRecommendation() {
        return finalRecommendation;
    }

    public void setFinalRecommendation(String finalRecommendation) {
        this.finalRecommendation = finalRecommendation;
    }
}
