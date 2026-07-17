package com.estateiq.service;

import com.estateiq.dto.RecentSearchRequestDTO;
import com.estateiq.entity.RecentSearch;
import java.util.List;

public interface RecentSearchService {

    void saveRecentSearch(String email, RecentSearchRequestDTO requestDTO);

    List<RecentSearch> getRecentSearches(String email);
}
