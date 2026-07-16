package com.estateiq.dto;

public class CompareResponseDTO {

    private Double buildTotalCost;
    private Double buyTotalCost;
    private String recommendation;

    public CompareResponseDTO() {
    }

    public CompareResponseDTO(Double buildTotalCost, Double buyTotalCost, String recommendation) {
        this.buildTotalCost = buildTotalCost;
        this.buyTotalCost = buyTotalCost;
        this.recommendation = recommendation;
    }

    public Double getBuildTotalCost() {
        return buildTotalCost;
    }

    public void setBuildTotalCost(Double buildTotalCost) {
        this.buildTotalCost = buildTotalCost;
    }

    public Double getBuyTotalCost() {
        return buyTotalCost;
    }

    public void setBuyTotalCost(Double buyTotalCost) {
        this.buyTotalCost = buyTotalCost;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
