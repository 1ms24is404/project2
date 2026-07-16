package com.estateiq.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
public class RecommendationRequestDTO {

    @Positive
    private Double monthlySalary;

    @Positive
    private Double budget;

    @Positive
    private Double propertyPrice;

    @Positive
    private Double emi;

    @NotBlank
    private String purpose;

    @NotBlank
    private String location;

    public RecommendationRequestDTO() {
    }

    public RecommendationRequestDTO(Double monthlySalary, Double budget, Double propertyPrice, Double emi, String purpose, String location) {
        this.monthlySalary = monthlySalary;
        this.budget = budget;
        this.propertyPrice = propertyPrice;
        this.emi = emi;
        this.purpose = purpose;
        this.location = location;
    }

    public Double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(Double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Double getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(Double propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public Double getEmi() {
        return emi;
    }

    public void setEmi(Double emi) {
        this.emi = emi;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
