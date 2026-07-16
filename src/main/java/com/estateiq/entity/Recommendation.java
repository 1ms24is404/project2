package com.estateiq.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long propertyId;

    @Column(columnDefinition = "TEXT")
    private String recommendationText;

    private Integer affordabilityScore;

    private Integer investmentScore;

    private LocalDateTime createdAt;

    public Recommendation() {
    }

    public Recommendation(Long id, Long userId, Long propertyId, String recommendationText, Integer affordabilityScore, Integer investmentScore, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.propertyId = propertyId;
        this.recommendationText = recommendationText;
        this.affordabilityScore = affordabilityScore;
        this.investmentScore = investmentScore;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getRecommendationText() {
        return recommendationText;
    }

    public void setRecommendationText(String recommendationText) {
        this.recommendationText = recommendationText;
    }

    public Integer getAffordabilityScore() {
        return affordabilityScore;
    }

    public void setAffordabilityScore(Integer affordabilityScore) {
        this.affordabilityScore = affordabilityScore;
    }

    public Integer getInvestmentScore() {
        return investmentScore;
    }

    public void setInvestmentScore(Integer investmentScore) {
        this.investmentScore = investmentScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
