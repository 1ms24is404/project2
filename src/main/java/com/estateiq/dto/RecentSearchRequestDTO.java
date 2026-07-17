package com.estateiq.dto;

import jakarta.validation.constraints.NotBlank;

public class RecentSearchRequestDTO {

    @NotBlank
    private String city;

    private String sublocation;

    private Integer bhk;

    private String budget;

    public RecentSearchRequestDTO() {
    }

    public RecentSearchRequestDTO(String city, String sublocation, Integer bhk, String budget) {
        this.city = city;
        this.sublocation = sublocation;
        this.bhk = bhk;
        this.budget = budget;
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

    public Integer getBhk() {
        return bhk;
    }

    public void setBhk(Integer bhk) {
        this.bhk = bhk;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }
}
