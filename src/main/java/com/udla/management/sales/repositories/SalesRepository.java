package com.udla.management.sales.repositories;

import com.udla.management.sales.models.SaleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing SaleModel database operations.
 */
@Repository
public interface SalesRepository extends JpaRepository<SaleModel, Long> {

    /**
     * Checks whether a sale exists with the given description.
     *
     * @param description sale description
     * @return true if exists, false otherwise
     */
    boolean existsByDescription(String description);
}