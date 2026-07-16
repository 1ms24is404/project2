package com.estateiq.dto;

import com.estateiq.entity.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
public class PropertyRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String location;

    private String builder;

    private PropertyType propertyType;

    @Positive
    private Double price;

    @Positive
    private Integer bhk;

    @Positive
    private Double area;

    private String amenities;

    private String description;

    public PropertyRequestDTO() {
    }

    public PropertyRequestDTO(String title, String location, String builder, PropertyType propertyType, Double price, Integer bhk, Double area, String amenities, String description) {
        this.title = title;
        this.location = location;
        this.builder = builder;
        this.propertyType = propertyType;
        this.price = price;
        this.bhk = bhk;
        this.area = area;
        this.amenities = amenities;
        this.description = description;
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
}
