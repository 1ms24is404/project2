package com.estateiq.controller;

import com.estateiq.dto.PropertyResponseDTO;
import com.estateiq.dto.WishlistRequestDTO;
import com.estateiq.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    @Operation(summary = "Add to wishlist", description = "Adds a property bookmark for the authenticated user.")
    public ResponseEntity<Void> addToWishlist(@Valid @RequestBody WishlistRequestDTO requestDTO, Principal principal) {
        wishlistService.addToWishlist(principal.getName(), requestDTO.getPropertyId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{propertyId}")
    @Operation(summary = "Remove from wishlist", description = "Removes a property bookmark for the authenticated user.")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Long propertyId, Principal principal) {
        wishlistService.removeFromWishlist(principal.getName(), propertyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get user wishlist", description = "Retrieves all properties bookmarked by the authenticated user.")
    public ResponseEntity<List<PropertyResponseDTO>> getWishlist(Principal principal) {
        return ResponseEntity.ok(wishlistService.getWishlist(principal.getName()));
    }
}
