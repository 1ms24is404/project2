package com.estateiq.service;

import com.estateiq.dto.AffordabilityRequestDTO;
import com.estateiq.dto.AffordabilityResponseDTO;
import com.estateiq.dto.CompareRequestDTO;
import com.estateiq.dto.CompareResponseDTO;
import com.estateiq.dto.EmiRequestDTO;
import com.estateiq.dto.EmiResponseDTO;

public interface FinanceService {

    EmiResponseDTO calculateEmi(EmiRequestDTO requestDTO);

    CompareResponseDTO compareBuyVsBuild(CompareRequestDTO requestDTO);

    AffordabilityResponseDTO calculateAffordability(AffordabilityRequestDTO requestDTO);
}
