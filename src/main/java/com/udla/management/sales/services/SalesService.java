package com.udla.management.sales.services;


import com.udla.management.inventory.models.InventoryModel;
import com.udla.management.inventory.services.InventoryService;
import com.udla.management.sales.models.SaleModel;
import com.udla.management.sales.repositories.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesService implements ISalesService{

    private final SalesRepository salesRepository;
    private final InventoryService inventoryService;

    @Override
    public List<SaleModel> getSales(){
        return salesRepository.findAll();
    };

    @Override
    public boolean saveSale(SaleModel sale){
        boolean saveInventory=false;
        InventoryModel inventoryModel = inventoryService.findInventoryByProduct(sale.getIdProduct());
        if(inventoryModel == null){
            inventoryModel.setIdProduct(sale.getIdProduct());
            inventoryModel.setQuantity(sale.getQuantity());
            saveInventory = inventoryService.saveToInventory(inventoryModel);
        }else{
            saveInventory = inventoryService.editInventory(inventoryModel);
        }
        if(saveInventory){
            salesRepository.save(sale);
            return true;
        }else{
            return false;
        }
    };

    @Override
    public boolean deleteSale(Long id){
        if(salesRepository.existsById(id)){
            SaleModel sale= salesRepository.findById(id).orElse(null);
            InventoryModel inventoryModel = inventoryService.findInventoryByProduct(sale.getIdProduct());
            if(inventoryModel.getQuantity()-sale.getQuantity()<0){
                return false;
            }
            inventoryModel.setQuantity(inventoryModel.getQuantity()-sale.getQuantity());
            salesRepository.deleteById(id);
            inventoryService.deleteFromInventory(id);
            return true;
        }
        return false;
    }

    @Override
    public SaleModel findSale(Long id){
        return salesRepository.findById(id).orElse(null);
    }

    @Override
    public boolean editSale(SaleModel sale){
        InventoryModel inventoryModel = inventoryService.findInventoryByProduct(sale.getIdProduct());
        if(inventoryModel == null) return false;
        if( inventoryModel.getQuantity()-sale.getQuantity() < 0){
            return false;
        }
        boolean saveInventory = inventoryService.editInventory(inventoryModel);
        if(saveInventory){
            if(salesRepository.existsById(sale.getIdSale())){
                salesRepository.save(sale);
                return true;
            }
        }else{
            return false;
        }
        return false;
    }
}
