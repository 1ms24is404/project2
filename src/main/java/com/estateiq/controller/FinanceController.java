package com.estateiq.controller;

import com.estateiq.dto.AffordabilityRequestDTO;
import com.estateiq.dto.AffordabilityResponseDTO;
import com.estateiq.dto.CompareRequestDTO;
import com.estateiq.dto.CompareResponseDTO;
import com.estateiq.dto.EmiRequestDTO;
import com.estateiq.dto.EmiResponseDTO;
import com.estateiq.service.FinanceService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/finance")
public class FinanceController {

    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @PostMapping("/emi")
    @Operation(summary = "Calculate EMI", description = "Calculates the monthly EMI using the standard EMI formula.")
    public ResponseEntity<EmiResponseDTO> calculateEmi(@Valid @RequestBody EmiRequestDTO requestDTO) {
        return ResponseEntity.ok(financeService.calculateEmi(requestDTO));
    }

    @PostMapping("/compare")
    @Operation(summary = "Compare buy vs build", description = "Compares build and buy costs including registration charges.")
    public ResponseEntity<CompareResponseDTO> compareBuyVsBuild(@Valid @RequestBody CompareRequestDTO requestDTO) {
        return ResponseEntity.ok(financeService.compareBuyVsBuild(requestDTO));
    }

    @PostMapping("/affordability")
    @Operation(summary = "Calculate affordability", description = "Calculates an affordability score from EMI and salary data.")
    public ResponseEntity<AffordabilityResponseDTO> calculateAffordability(@Valid @RequestBody AffordabilityRequestDTO requestDTO) {
        return ResponseEntity.ok(financeService.calculateAffordability(requestDTO));
    }
}
