package com.estateiq.controller;

import com.estateiq.dto.RecentSearchRequestDTO;
import com.estateiq.entity.RecentSearch;
import com.estateiq.service.RecentSearchService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recent-searches")
public class RecentSearchController {

    private final RecentSearchService recentSearchService;

    public RecentSearchController(RecentSearchService recentSearchService) {
        this.recentSearchService = recentSearchService;
    }

    @PostMapping
    @Operation(summary = "Save recent search", description = "Saves search criteria used by the authenticated user.")
    public ResponseEntity<Void> saveRecentSearch(@Valid @RequestBody RecentSearchRequestDTO requestDTO, Principal principal) {
        recentSearchService.saveRecentSearch(principal.getName(), requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "Get recent searches", description = "Retrieves recent searches of the authenticated user.")
    public ResponseEntity<List<RecentSearch>> getRecentSearches(Principal principal) {
        return ResponseEntity.ok(recentSearchService.getRecentSearches(principal.getName()));
    }
}
