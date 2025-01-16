package com.udla.management.inventory.services;

import com.udla.management.inventory.models.InventoryModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IInvetoryService {

    public List<InventoryModel> getInventory();

    public boolean saveToInventory(InventoryModel inventory);

    public boolean deleteFromInventory(Long id);

    public InventoryModel findOnInventory(Long id);

    public boolean editInventory (InventoryModel inventory);
}
