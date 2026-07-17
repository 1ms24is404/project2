package com.estateiq.dto;

import java.util.List;
import java.util.Map;

public class DashboardStatsResponseDTO {

    private Map<String, Double> averagePricePerCity;
    private List<String> priceTrends;
    private Map<String, Long> budgetDistribution;
    private Map<String, Long> propertyTypeDistribution;

    public DashboardStatsResponseDTO() {
    }

    public DashboardStatsResponseDTO(Map<String, Double> averagePricePerCity, List<String> priceTrends, Map<String, Long> budgetDistribution, Map<String, Long> propertyTypeDistribution) {
        this.averagePricePerCity = averagePricePerCity;
        this.priceTrends = priceTrends;
        this.budgetDistribution = budgetDistribution;
        this.propertyTypeDistribution = propertyTypeDistribution;
    }

    public Map<String, Double> getAveragePricePerCity() {
        return averagePricePerCity;
    }

    public void setAveragePricePerCity(Map<String, Double> averagePricePerCity) {
        this.averagePricePerCity = averagePricePerCity;
    }

    public List<String> getPriceTrends() {
        return priceTrends;
    }

    public void setPriceTrends(List<String> priceTrends) {
        this.priceTrends = priceTrends;
    }

    public Map<String, Long> getBudgetDistribution() {
        return budgetDistribution;
    }

    public void setBudgetDistribution(Map<String, Long> budgetDistribution) {
        this.budgetDistribution = budgetDistribution;
    }

    public Map<String, Long> getPropertyTypeDistribution() {
        return propertyTypeDistribution;
    }

    public void setPropertyTypeDistribution(Map<String, Long> propertyTypeDistribution) {
        this.propertyTypeDistribution = propertyTypeDistribution;
    }
}
