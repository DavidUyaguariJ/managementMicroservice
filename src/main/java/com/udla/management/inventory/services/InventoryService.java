package com.udla.management.inventory.services;

import com.udla.management.inventory.models.InventoryModel;
import com.udla.management.inventory.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService implements IInvetoryService{

    private final InventoryRepository inventoryRepository;

    @Override
    public List<InventoryModel> getInventory(){
        return inventoryRepository.findAll();
    };

    @Override
    public boolean saveToInventory(InventoryModel inventory){
        inventoryRepository.save(inventory);
        return true;
    };

    @Override
    public boolean deleteFromInventory(Long id){
        if(inventoryRepository.existsById(id)){
            inventoryRepository.deleteById(id);
            return true;
        }
        return false;
    };

    @Override
    public InventoryModel findOnInventory(Long id){
        return inventoryRepository.findById(id).orElse(null);
    }

    @Override
    public boolean editInventory (InventoryModel inventory){
        if(inventoryRepository.existsById(inventory.getId_inventory())){
            inventoryRepository.save(inventory);
            return true;
        }
        return false;
    };
}

