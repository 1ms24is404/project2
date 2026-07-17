package com.estateiq.service.impl;

import com.estateiq.dto.RecentSearchRequestDTO;
import com.estateiq.entity.RecentSearch;
import com.estateiq.repository.RecentSearchRepository;
import com.estateiq.service.RecentSearchService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecentSearchServiceImpl implements RecentSearchService {

    private final RecentSearchRepository recentSearchRepository;

    public RecentSearchServiceImpl(RecentSearchRepository recentSearchRepository) {
        this.recentSearchRepository = recentSearchRepository;
    }

    @Override
    @Transactional
    public void saveRecentSearch(String email, RecentSearchRequestDTO requestDTO) {
        // Save the new search
        RecentSearch search = new RecentSearch(
                email,
                requestDTO.getCity(),
                requestDTO.getSublocation(),
                requestDTO.getBhk(),
                requestDTO.getBudget(),
                LocalDateTime.now()
        );
        recentSearchRepository.save(search);

        // Keep only top 5 recent searches
        List<RecentSearch> searches = recentSearchRepository.findByUserEmailOrderByTimestampDesc(email);
        if (searches.size() > 5) {
            List<RecentSearch> toDelete = searches.subList(5, searches.size());
            recentSearchRepository.deleteAll(toDelete);
        }
    }

    @Override
    public List<RecentSearch> getRecentSearches(String email) {
        return recentSearchRepository.findByUserEmailOrderByTimestampDesc(email);
    }
}
