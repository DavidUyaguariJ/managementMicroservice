package com.udla.management.sales.repositories;

import com.udla.management.sales.models.SaleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<SaleModel, Long> {

    boolean existsByDescription(String description);

}
