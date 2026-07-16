package com.estateiq.repository;

import com.estateiq.entity.Property;
import com.estateiq.entity.PropertyType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {

    @Query("""
            select p
            from Property p
            where (:location is null or lower(p.location) like lower(concat('%', :location, '%')))
              and (:propertyType is null or p.propertyType = :propertyType)
              and (:bhk is null or p.bhk = :bhk)
            """)
    List<Property> findByFilters(@Param("location") String location,
                                 @Param("propertyType") PropertyType propertyType,
                                 @Param("bhk") Integer bhk);
}
