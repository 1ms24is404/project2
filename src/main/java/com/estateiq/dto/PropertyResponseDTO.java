package com.estateiq.dto;

import com.estateiq.entity.PropertyType;
import java.time.LocalDateTime;
public class PropertyResponseDTO {

    private Long id;
    private String title;
    private String location;
    private String builder;
    private PropertyType propertyType;
    private Double price;
    private Integer bhk;
    private Double area;
    private String amenities;
    private String description;
    private LocalDateTime createdAt;

    private String city;
    private String sublocation;
    private String possessionStatus;
    private Double latitude;
    private Double longitude;
    private String coverImageUrl;
    private String nearbySchools;
    private String nearbyHospitals;
    private String nearbyMetro;
    private Double investmentRating;
    private Double rentalYield;
    private String priceHistory;
    private Double rating;

    public PropertyResponseDTO() {
    }

    public PropertyResponseDTO(Long id, String title, String location, String builder, PropertyType propertyType, Double price, Integer bhk, Double area, String amenities, String description, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.builder = builder;
        this.propertyType = propertyType;
        this.price = price;
        this.bhk = bhk;
        this.area = area;
        this.amenities = amenities;
        this.description = description;
        this.createdAt = createdAt;
    }

    public PropertyResponseDTO(Long id, String title, String location, String builder, PropertyType propertyType, Double price, Integer bhk, Double area, String amenities, String description, LocalDateTime createdAt, String city, String sublocation, String possessionStatus, Double latitude, Double longitude, String coverImageUrl, String nearbySchools, String nearbyHospitals, String nearbyMetro, Double investmentRating, Double rentalYield, String priceHistory, Double rating) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.builder = builder;
        this.propertyType = propertyType;
        this.price = price;
        this.bhk = bhk;
        this.area = area;
        this.amenities = amenities;
        this.description = description;
        this.createdAt = createdAt;
        this.city = city;
        this.sublocation = sublocation;
        this.possessionStatus = possessionStatus;
        this.latitude = latitude;
        this.longitude = longitude;
        this.coverImageUrl = coverImageUrl;
        this.nearbySchools = nearbySchools;
        this.nearbyHospitals = nearbyHospitals;
        this.nearbyMetro = nearbyMetro;
        this.investmentRating = investmentRating;
        this.rentalYield = rentalYield;
        this.priceHistory = priceHistory;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBuilder() {
        return builder;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getBhk() {
        return bhk;
    }

    public void setBhk(Integer bhk) {
        this.bhk = bhk;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSublocation() {
        return sublocation;
    }

    public void setSublocation(String sublocation) {
        this.sublocation = sublocation;
    }

    public String getPossessionStatus() {
        return possessionStatus;
    }

    public void setPossessionStatus(String possessionStatus) {
        this.possessionStatus = possessionStatus;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getNearbySchools() {
        return nearbySchools;
    }

    public void setNearbySchools(String nearbySchools) {
        this.nearbySchools = nearbySchools;
    }

    public String getNearbyHospitals() {
        return nearbyHospitals;
    }

    public void setNearbyHospitals(String nearbyHospitals) {
        this.nearbyHospitals = nearbyHospitals;
    }

    public String getNearbyMetro() {
        return nearbyMetro;
    }

    public void setNearbyMetro(String nearbyMetro) {
        this.nearbyMetro = nearbyMetro;
    }

    public Double getInvestmentRating() {
        return investmentRating;
    }

    public void setInvestmentRating(Double investmentRating) {
        this.investmentRating = investmentRating;
    }

    public Double getRentalYield() {
        return rentalYield;
    }

    public void setRentalYield(Double rentalYield) {
        this.rentalYield = rentalYield;
    }

    public String getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(String priceHistory) {
        this.priceHistory = priceHistory;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
