package com.estateiq.service.impl;

import com.estateiq.dto.PropertyRequestDTO;
import com.estateiq.dto.PropertyResponseDTO;
import com.estateiq.entity.Property;
import com.estateiq.entity.PropertyType;
import com.estateiq.exception.PropertyNotFoundException;
import com.estateiq.repository.PropertyRepository;
import com.estateiq.service.PropertyService;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    @Transactional
    public PropertyResponseDTO createProperty(PropertyRequestDTO requestDTO) {
        Property property = toEntity(requestDTO);
        property.setCreatedAt(LocalDateTime.now());
        return toResponse(propertyRepository.save(property));
    }

    @Override
    public PropertyResponseDTO getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found with id: " + id));
        return toResponse(property);
    }

    @Override
    @Transactional
    public PropertyResponseDTO updateProperty(Long id, PropertyRequestDTO requestDTO) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found with id: " + id));

        property.setTitle(requestDTO.getTitle());
        property.setLocation(requestDTO.getLocation());
        property.setBuilder(requestDTO.getBuilder());
        property.setPropertyType(requestDTO.getPropertyType());
        property.setPrice(requestDTO.getPrice());
        property.setBhk(requestDTO.getBhk());
        property.setArea(requestDTO.getArea());
        property.setAmenities(requestDTO.getAmenities());
        property.setDescription(requestDTO.getDescription());

        return toResponse(propertyRepository.save(property));
    }

    @Override
    @Transactional
    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new PropertyNotFoundException("Property not found with id: " + id);
        }
        propertyRepository.deleteById(id);
    }

    @Override
    public Page<PropertyResponseDTO> getAllProperties(String location,
                                                      PropertyType propertyType,
                                                      Integer bhk,
                                                      Double minPrice,
                                                      Double maxPrice,
                                                      String city,
                                                      String sublocation,
                                                      String possessionStatus,
                                                      Pageable pageable) {
        Specification<Property> specification = (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            if (StringUtils.hasText(location)) {
                predicates.getExpressions().add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("location")),
                                "%" + location.toLowerCase() + "%"));
            }

            if (propertyType != null) {
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("propertyType"), propertyType));
            }

            if (bhk != null) {
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("bhk"), bhk));
            }

            if (minPrice != null) {
                predicates.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if (maxPrice != null) {
                predicates.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (StringUtils.hasText(city)) {
                predicates.getExpressions().add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("city")),
                        city.toLowerCase()));
            }

            if (StringUtils.hasText(sublocation)) {
                predicates.getExpressions().add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("sublocation")),
                        sublocation.toLowerCase()));
            }

            if (StringUtils.hasText(possessionStatus)) {
                predicates.getExpressions().add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("possessionStatus")),
                        possessionStatus.toLowerCase()));
            }

            return predicates;
        };

        return propertyRepository.findAll(specification, pageable).map(this::toResponse);
    }

    private Property toEntity(PropertyRequestDTO requestDTO) {
        Property property = new Property();
        property.setTitle(requestDTO.getTitle());
        property.setLocation(requestDTO.getLocation());
        property.setBuilder(requestDTO.getBuilder());
        property.setPropertyType(requestDTO.getPropertyType());
        property.setPrice(requestDTO.getPrice());
        property.setBhk(requestDTO.getBhk());
        property.setArea(requestDTO.getArea());
        property.setAmenities(requestDTO.getAmenities());
        property.setDescription(requestDTO.getDescription());
        return property;
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
