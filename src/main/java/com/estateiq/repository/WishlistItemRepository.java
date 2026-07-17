package com.estateiq.repository;

import com.estateiq.entity.WishlistItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByUserEmail(String userEmail);

    Optional<WishlistItem> findByUserEmailAndPropertyId(String userEmail, Long propertyId);

    void deleteByUserEmailAndPropertyId(String userEmail, Long propertyId);
}
