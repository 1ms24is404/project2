package com.estateiq.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.estateiq.dto.AffordabilityRequestDTO;
import com.estateiq.dto.AffordabilityResponseDTO;
import com.estateiq.dto.CompareRequestDTO;
import com.estateiq.dto.EmiRequestDTO;
import com.estateiq.dto.EmiResponseDTO;
import org.junit.jupiter.api.Test;

class FinanceServiceImplTest {

    private final FinanceServiceImpl financeService = new FinanceServiceImpl();

    @Test
    void calculateEmiShouldReturnCorrectValueForStandardLoan() {
        EmiRequestDTO requestDTO = new EmiRequestDTO(1_000_000d, 12d, 12);

        EmiResponseDTO responseDTO = financeService.calculateEmi(requestDTO);

        assertEquals(88848.79, responseDTO.getEmi(), 0.1);
        assertEquals(1_066_185.48, responseDTO.getTotalAmountPayable(), 0.1);
        assertEquals(66_185.48, responseDTO.getTotalInterestPayable(), 0.1);
    }

    @Test
    void calculateAffordabilityShouldScoreFavorableCaseAtUpperThreshold() {
        AffordabilityRequestDTO requestDTO = new AffordabilityRequestDTO(100_000d, 20_000d, 300_000d, 1_000_000d);

        AffordabilityResponseDTO responseDTO = financeService.calculateAffordability(requestDTO);

        assertEquals(100, responseDTO.getScore());
        assertTrue(responseDTO.getReasoning().contains("20.0%"));
    }

    @Test
    void calculateAffordabilityShouldScorePoorCaseAboveThresholds() {
        AffordabilityRequestDTO requestDTO = new AffordabilityRequestDTO(100_000d, 40_100d, 50_000d, 1_000_000d);

        AffordabilityResponseDTO responseDTO = financeService.calculateAffordability(requestDTO);

        assertEquals(15, responseDTO.getScore());
        assertTrue(responseDTO.getReasoning().contains("40.1%"));
    }

    @Test
    void compareBuyVsBuildShouldPreferLowerTotalCost() {
        CompareRequestDTO requestDTO = new CompareRequestDTO(2_000_000d, 1_000_000d, 4_000_000d, 5d);

        assertTrue(financeService.compareBuyVsBuild(requestDTO).getRecommendation().contains("Building"));
    }
}