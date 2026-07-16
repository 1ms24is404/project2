package com.estateiq.controller;

import com.estateiq.dto.RecommendationRequestDTO;
import com.estateiq.dto.RecommendationResponseDTO;
import com.estateiq.service.AIRecommendationService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recommend")
public class AIRecommendationController {

    private final AIRecommendationService aiRecommendationService;

    public AIRecommendationController(AIRecommendationService aiRecommendationService) {
        this.aiRecommendationService = aiRecommendationService;
    }

    @PostMapping
    @Operation(summary = "Generate recommendation", description = "Generates an AI-backed recommendation using Gemini and JWT-protected access.")
    public ResponseEntity<RecommendationResponseDTO> recommend(@Valid @RequestBody RecommendationRequestDTO requestDTO) {
        return ResponseEntity.ok(aiRecommendationService.generateRecommendation(requestDTO));
    }
}
