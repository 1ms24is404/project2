package com.estateiq.service;

import com.estateiq.dto.RecommendationRequestDTO;
import com.estateiq.dto.RecommendationResponseDTO;

public interface AIRecommendationService {

    RecommendationResponseDTO generateRecommendation(RecommendationRequestDTO requestDTO);
}
