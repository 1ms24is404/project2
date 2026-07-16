package com.estateiq.dto;

import jakarta.validation.constraints.Positive;
public class EmiRequestDTO {

    @Positive
    private Double principal;

    @Positive
    private Double annualInterestRate;

    @Positive
    private Integer tenureMonths;

    public EmiRequestDTO() {
    }

    public EmiRequestDTO(Double principal, Double annualInterestRate, Integer tenureMonths) {
        this.principal = principal;
        this.annualInterestRate = annualInterestRate;
        this.tenureMonths = tenureMonths;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(Double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public Integer getTenureMonths() {
        return tenureMonths;
    }

    public void setTenureMonths(Integer tenureMonths) {
        this.tenureMonths = tenureMonths;
    }
}
