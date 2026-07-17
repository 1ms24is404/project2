package com.estateiq.controller;

import com.estateiq.dto.PropertyRequestDTO;
import com.estateiq.dto.PropertyResponseDTO;
import com.estateiq.entity.PropertyType;
import com.estateiq.service.PropertyService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    @Operation(summary = "Create property", description = "Creates a new property listing.")
    public ResponseEntity<PropertyResponseDTO> createProperty(@Valid @RequestBody PropertyRequestDTO requestDTO) {
        return new ResponseEntity<>(propertyService.createProperty(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get property by id", description = "Returns a single property by its identifier.")
    public ResponseEntity<PropertyResponseDTO> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update property", description = "Updates an existing property listing.")
    public ResponseEntity<PropertyResponseDTO> updateProperty(@PathVariable Long id,
                                                              @Valid @RequestBody PropertyRequestDTO requestDTO) {
        return ResponseEntity.ok(propertyService.updateProperty(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete property", description = "Deletes a property listing by id.")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get all properties", description = "Returns paginated properties with optional filters.")
    public ResponseEntity<Page<PropertyResponseDTO>> getAllProperties(@RequestParam(required = false) String location,
                                                                      @RequestParam(required = false) PropertyType propertyType,
                                                                      @RequestParam(required = false) Integer bhk,
                                                                      @RequestParam(required = false) Double minPrice,
                                                                      @RequestParam(required = false) Double maxPrice,
                                                                      @RequestParam(required = false) String city,
                                                                      @RequestParam(required = false) String sublocation,
                                                                      @RequestParam(required = false) String possessionStatus,
                                                                      Pageable pageable) {
        return ResponseEntity.ok(propertyService.getAllProperties(location, propertyType, bhk, minPrice, maxPrice, city, sublocation, possessionStatus, pageable));
    }

    @GetMapping("/locations")
    @Operation(summary = "Get seeded locations", description = "Returns a map of cities and their sublocations.")
    public ResponseEntity<Map<String, List<String>>> getLocations() {
        Map<String, List<String>> locations = Map.of(
            "Bangalore", List.of("Whitefield", "Electronic City", "Koramangala", "Indiranagar", "HSR Layout"),
            "Mumbai", List.of("Bandra", "Andheri", "Powai", "Navi Mumbai", "Thane"),
            "Hyderabad", List.of("HITEC City", "Gachibowli", "Kondapur", "Jubilee Hills", "Madhapur"),
            "Pune", List.of("Hinjawadi", "Baner", "Wakad", "Kharadi", "Viman Nagar"),
            "Chennai", List.of("OMR", "Velachery", "Anna Nagar", "Tambaram", "Sholinganallur"),
            "Delhi NCR", List.of("Gurgaon", "Noida", "Dwarka", "Greater Noida", "Rohini")
        );
        return ResponseEntity.ok(locations);
    }
}
