package com.estateiq.dto;

import jakarta.validation.constraints.Positive;
public class AffordabilityRequestDTO {

    @Positive
    private Double monthlySalary;

    @Positive
    private Double emi;

    @Positive
    private Double downPayment;

    @Positive
    private Double propertyPrice;

    public AffordabilityRequestDTO() {
    }

    public AffordabilityRequestDTO(Double monthlySalary, Double emi, Double downPayment, Double propertyPrice) {
        this.monthlySalary = monthlySalary;
        this.emi = emi;
        this.downPayment = downPayment;
        this.propertyPrice = propertyPrice;
    }

    public Double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(Double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public Double getEmi() {
        return emi;
    }

    public void setEmi(Double emi) {
        this.emi = emi;
    }

    public Double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(Double downPayment) {
        this.downPayment = downPayment;
    }

    public Double getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(Double propertyPrice) {
        this.propertyPrice = propertyPrice;
    }
}
