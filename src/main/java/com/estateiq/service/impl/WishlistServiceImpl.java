package com.estateiq.service.impl;

import com.estateiq.dto.PropertyResponseDTO;
import com.estateiq.entity.Property;
import com.estateiq.entity.WishlistItem;
import com.estateiq.exception.PropertyNotFoundException;
import com.estateiq.repository.PropertyRepository;
import com.estateiq.repository.WishlistItemRepository;
import com.estateiq.service.WishlistService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistItemRepository wishlistRepository;
    private final PropertyRepository propertyRepository;

    public WishlistServiceImpl(WishlistItemRepository wishlistRepository, PropertyRepository propertyRepository) {
        this.wishlistRepository = wishlistRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    @Transactional
    public void addToWishlist(String email, Long propertyId) {
        if (!propertyRepository.existsById(propertyId)) {
            throw new PropertyNotFoundException("Property not found with id: " + propertyId);
        }
        // Avoid duplicate bookmarks
        if (wishlistRepository.findByUserEmailAndPropertyId(email, propertyId).isEmpty()) {
            wishlistRepository.save(new WishlistItem(email, propertyId));
        }
    }

    @Override
    @Transactional
    public void removeFromWishlist(String email, Long propertyId) {
        wishlistRepository.deleteByUserEmailAndPropertyId(email, propertyId);
    }

    @Override
    public List<PropertyResponseDTO> getWishlist(String email) {
        List<WishlistItem> items = wishlistRepository.findByUserEmail(email);
        List<Long> propertyIds = items.stream().map(WishlistItem::getPropertyId).collect(Collectors.toList());
        List<Property> properties = propertyRepository.findAllById(propertyIds);
        return properties.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private PropertyResponseDTO toResponse(Property property) {
        return new PropertyResponseDTO(
                property.getId(),
                property.getTitle(),
                property.getLocation(),
                property.getBuilder(),
                property.getPropertyType(),
                property.getPrice(),
                property.getBhk(),
                property.getArea(),
                property.getAmenities(),
                property.getDescription(),
                property.getCreatedAt(),
                property.getCity(),
                property.getSublocation(),
                property.getPossessionStatus(),
                property.getLatitude(),
                property.getLongitude(),
                property.getCoverImageUrl(),
                property.getNearbySchools(),
                property.getNearbyHospitals(),
                property.getNearbyMetro(),
                property.getInvestmentRating(),
                property.getRentalYield(),
                property.getPriceHistory(),
                property.getRating());
    }
}
