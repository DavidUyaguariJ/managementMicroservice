package com.udla.management.sales.repositories;

import com.udla.management.sales.models.SaleModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SalesRepository extends JpaRepository<SaleModel, Long> {

}
