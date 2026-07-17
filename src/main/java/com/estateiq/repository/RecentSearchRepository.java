package com.estateiq.repository;

import com.estateiq.entity.RecentSearch;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentSearchRepository extends JpaRepository<RecentSearch, Long> {

    List<RecentSearch> findByUserEmailOrderByTimestampDesc(String userEmail);
}
