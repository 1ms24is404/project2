package com.estateiq.service.impl;

import com.estateiq.dto.DashboardStatsResponseDTO;
import com.estateiq.entity.Property;
import com.estateiq.repository.PropertyRepository;
import com.estateiq.service.DashboardService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final PropertyRepository propertyRepository;

    public DashboardServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public DashboardStatsResponseDTO getDashboardStats() {
        List<Property> properties = propertyRepository.findAll();

        // 1. Average price per city
        Map<String, List<Property>> propertiesByCity = properties.stream()
                .filter(p -> p.getCity() != null)
                .collect(Collectors.groupingBy(Property::getCity));

        Map<String, Double> averagePricePerCity = new HashMap<>();
        propertiesByCity.forEach((city, list) -> {
            double avg = list.stream()
                    .mapToDouble(Property::getPrice)
                    .average()
                    .orElse(0.0);
            averagePricePerCity.put(city, Math.round(avg * 100.0) / 100.0);
        });

        // 2. Budget distribution
        Map<String, Long> budgetDistribution = new HashMap<>();
        budgetDistribution.put("Under 50L", 0L);
        budgetDistribution.put("50L - 1Cr", 0L);
        budgetDistribution.put("1Cr - 2Cr", 0L);
        budgetDistribution.put("2Cr+", 0L);

        for (Property p : properties) {
            double price = p.getPrice();
            if (price < 5000000) {
                budgetDistribution.put("Under 50L", budgetDistribution.get("Under 50L") + 1);
            } else if (price < 10000000) {
                budgetDistribution.put("50L - 1Cr", budgetDistribution.get("50L - 1Cr") + 1);
            } else if (price < 20000000) {
                budgetDistribution.put("1Cr - 2Cr", budgetDistribution.get("1Cr - 2Cr") + 1);
            } else {
                budgetDistribution.put("2Cr+", budgetDistribution.get("2Cr+") + 1);
            }
        }

        // 3. Property Type distribution
        Map<String, Long> propertyTypeDistribution = new HashMap<>();
        propertyTypeDistribution.put("FLAT", 0L);
        propertyTypeDistribution.put("HOUSE", 0L);
        propertyTypeDistribution.put("PLOT", 0L);

        for (Property p : properties) {
            if (p.getPropertyType() != null) {
                String typeStr = p.getPropertyType().name();
                propertyTypeDistribution.put(typeStr, propertyTypeDistribution.getOrDefault(typeStr, 0L) + 1);
            }
        }

        // 4. Price trends (mock trends data)
        List<String> priceTrends = List.of("2023:+5.4%", "2024:+7.8%", "2025:+9.2%", "2026:+11.5%");

        return new DashboardStatsResponseDTO(averagePricePerCity, priceTrends, budgetDistribution, propertyTypeDistribution);
    }
}
