package com.udla.management.inventory.repositories;

import com.udla.management.inventory.models.InventoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryModel, Long> {

    Optional<InventoryModel> findByIdeProduct(UUID ideProduct);

}