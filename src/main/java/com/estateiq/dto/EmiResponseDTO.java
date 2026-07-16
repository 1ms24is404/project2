package com.estateiq.dto;

public class EmiResponseDTO {

    private Double emi;
    private Double totalAmountPayable;
    private Double totalInterestPayable;

    public EmiResponseDTO() {
    }

    public EmiResponseDTO(Double emi, Double totalAmountPayable, Double totalInterestPayable) {
        this.emi = emi;
        this.totalAmountPayable = totalAmountPayable;
        this.totalInterestPayable = totalInterestPayable;
    }

    public Double getEmi() {
        return emi;
    }

    public void setEmi(Double emi) {
        this.emi = emi;
    }

    public Double getTotalAmountPayable() {
        return totalAmountPayable;
    }

    public void setTotalAmountPayable(Double totalAmountPayable) {
        this.totalAmountPayable = totalAmountPayable;
    }

    public Double getTotalInterestPayable() {
        return totalInterestPayable;
    }

    public void setTotalInterestPayable(Double totalInterestPayable) {
        this.totalInterestPayable = totalInterestPayable;
    }
}
