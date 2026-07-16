package com.estateiq.service.impl;

import com.estateiq.dto.AffordabilityRequestDTO;
import com.estateiq.dto.AffordabilityResponseDTO;
import com.estateiq.dto.CompareRequestDTO;
import com.estateiq.dto.CompareResponseDTO;
import com.estateiq.dto.EmiRequestDTO;
import com.estateiq.dto.EmiResponseDTO;
import com.estateiq.service.FinanceService;
import org.springframework.stereotype.Service;

@Service
public class FinanceServiceImpl implements FinanceService {

    @Override
    public EmiResponseDTO calculateEmi(EmiRequestDTO requestDTO) {
        double monthlyRate = (requestDTO.getAnnualInterestRate() / 12.0) / 100.0;
        int months = requestDTO.getTenureMonths();
        double principal = requestDTO.getPrincipal();

        double emi;
        if (monthlyRate == 0) {
            emi = principal / months;
        } else {
            double factor = Math.pow(1 + monthlyRate, months);
            emi = principal * monthlyRate * factor / (factor - 1);
        }

        double totalAmountPayable = emi * months;
        double totalInterestPayable = totalAmountPayable - principal;
        return new EmiResponseDTO(round(emi), round(totalAmountPayable), round(totalInterestPayable));
    }

    @Override
    public CompareResponseDTO compareBuyVsBuild(CompareRequestDTO requestDTO) {
        double buildTotalCost = requestDTO.getLandCost()
                + requestDTO.getConstructionCost()
                + (requestDTO.getLandCost() * requestDTO.getRegistrationPercent() / 100.0);

        double buyTotalCost = requestDTO.getBuyCost()
                + (requestDTO.getBuyCost() * requestDTO.getRegistrationPercent() / 100.0);

        String recommendation = buildTotalCost <= buyTotalCost
                ? "Building is financially better based on the provided costs."
                : "Buying is financially better based on the provided costs.";

        return new CompareResponseDTO(round(buildTotalCost), round(buyTotalCost), recommendation);
    }

    @Override
    public AffordabilityResponseDTO calculateAffordability(AffordabilityRequestDTO requestDTO) {
        double emiToSalaryRatio = requestDTO.getEmi() / requestDTO.getMonthlySalary();
        double downPaymentCoverage = requestDTO.getDownPayment() / requestDTO.getPropertyPrice();

        int emiScore;
        if (emiToSalaryRatio <= 0.2) {
            emiScore = 70;
        } else if (emiToSalaryRatio <= 0.3) {
            emiScore = 55;
        } else if (emiToSalaryRatio <= 0.4) {
            emiScore = 35;
        } else {
            emiScore = 15;
        }

        int downPaymentScore;
        if (downPaymentCoverage >= 0.3) {
            downPaymentScore = 30;
        } else if (downPaymentCoverage >= 0.2) {
            downPaymentScore = 20;
        } else if (downPaymentCoverage >= 0.1) {
            downPaymentScore = 10;
        } else {
            downPaymentScore = 0;
        }

        int totalScore = Math.min(100, emiScore + downPaymentScore);
        String reasoning = "EMI-to-salary ratio is " + roundPercentage(emiToSalaryRatio)
                + " and down payment coverage is " + roundPercentage(downPaymentCoverage)
                + ", resulting in an affordability score of " + totalScore + ".";

        return new AffordabilityResponseDTO(totalScore, reasoning);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private String roundPercentage(double value) {
        return String.valueOf(Math.round(value * 1000.0) / 10.0) + "%";
    }
}
