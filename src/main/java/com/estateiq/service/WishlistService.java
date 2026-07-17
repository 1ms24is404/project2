package com.estateiq.service;

import com.estateiq.dto.PropertyResponseDTO;
import java.util.List;

public interface WishlistService {

    void addToWishlist(String email, Long propertyId);

    void removeFromWishlist(String email, Long propertyId);

    List<PropertyResponseDTO> getWishlist(String email);
}
