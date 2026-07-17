package com.estateiq.dto;

import jakarta.validation.constraints.NotNull;

public class WishlistRequestDTO {

    @NotNull
    private Long propertyId;

    public WishlistRequestDTO() {
    }

    public WishlistRequestDTO(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }
}
