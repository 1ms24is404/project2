package com.estateiq.dto;

public class AffordabilityResponseDTO {

    private Integer score;
    private String reasoning;

    public AffordabilityResponseDTO() {
    }

    public AffordabilityResponseDTO(Integer score, String reasoning) {
        this.score = score;
        this.reasoning = reasoning;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getReasoning() {
        return reasoning;
    }

    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }
}
