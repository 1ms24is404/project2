package com.estateiq.dto;

import jakarta.validation.constraints.Positive;
public class CompareRequestDTO {

    @Positive
    private Double landCost;

    @Positive
    private Double constructionCost;

    @Positive
    private Double buyCost;

    @Positive
    private Double registrationPercent;

    public CompareRequestDTO() {
    }

    public CompareRequestDTO(Double landCost, Double constructionCost, Double buyCost, Double registrationPercent) {
        this.landCost = landCost;
        this.constructionCost = constructionCost;
        this.buyCost = buyCost;
        this.registrationPercent = registrationPercent;
    }

    public Double getLandCost() {
        return landCost;
    }

    public void setLandCost(Double landCost) {
        this.landCost = landCost;
    }

    public Double getConstructionCost() {
        return constructionCost;
    }

    public void setConstructionCost(Double constructionCost) {
        this.constructionCost = constructionCost;
    }

    public Double getBuyCost() {
        return buyCost;
    }

    public void setBuyCost(Double buyCost) {
        this.buyCost = buyCost;
    }

    public Double getRegistrationPercent() {
        return registrationPercent;
    }

    public void setRegistrationPercent(Double registrationPercent) {
        this.registrationPercent = registrationPercent;
    }
}
