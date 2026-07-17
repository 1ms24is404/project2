package com.estateiq.service;

import com.estateiq.dto.PropertyRequestDTO;
import com.estateiq.dto.PropertyResponseDTO;
import com.estateiq.entity.PropertyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PropertyService {

    PropertyResponseDTO createProperty(PropertyRequestDTO requestDTO);

    PropertyResponseDTO getPropertyById(Long id);

    PropertyResponseDTO updateProperty(Long id, PropertyRequestDTO requestDTO);

    void deleteProperty(Long id);

    Page<PropertyResponseDTO> getAllProperties(String location,
                                               PropertyType propertyType,
                                               Integer bhk,
                                               Double minPrice,
                                               Double maxPrice,
                                               String city,
                                               String sublocation,
                                               String possessionStatus,
                                               Pageable pageable);
}
