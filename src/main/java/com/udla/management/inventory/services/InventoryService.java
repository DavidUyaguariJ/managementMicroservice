package com.udla.management.inventory.services;

import com.udla.management.inventory.models.InventoryModel;
import com.udla.management.inventory.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        if(inventory.getQuantity()<0){
            return false;
        }
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
        InventoryModel inventoryValue= inventoryRepository.findById(inventory.getIdInventory()).orElse(null);
        if(inventoryValue!=null && (inventoryValue.getQuantity()-inventory.getQuantity())>0){
            inventoryRepository.save(inventory);
            return true;
        }
        return false;
    };

    public InventoryModel findInventoryByProduct(UUID idProduct) {
        return inventoryRepository.findByIdProduct(idProduct).orElse(null);
    }
}