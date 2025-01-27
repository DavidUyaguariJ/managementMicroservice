package com.udla.management.purchases.services;

import com.udla.management.inventory.models.InventoryModel;
import com.udla.management.inventory.services.InventoryService;
import com.udla.management.purchases.models.PurchaseModel;
import com.udla.management.purchases.repositories.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService implements IPurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final InventoryService inventoryService;

    @Override
    public List<PurchaseModel> getPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public boolean savePurchase(PurchaseModel purchase) {
        boolean saveInventory = false;
        InventoryModel inventoryModel = inventoryService.findInventoryByProduct(purchase.getIdeProduct());
        if (inventoryModel == null) {
            inventoryModel = new InventoryModel();
            inventoryModel.setIdeProduct(purchase.getIdeProduct());
            inventoryModel.setQuantity(purchase.getQuantity());
            saveInventory = inventoryService.saveToInventory(inventoryModel);
        } else {
            saveInventory = inventoryService.editInventory(inventoryModel);
        }
        if (saveInventory) {
            purchaseRepository.save(purchase);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deletePurchase(Long id) {
        if (purchaseRepository.existsById(id)) {
            PurchaseModel purchase = purchaseRepository.findById(id).orElse(null);
            InventoryModel inventoryModel = inventoryService.findInventoryByProduct(purchase.getIdeProduct());
            if (inventoryModel.getQuantity() - purchase.getQuantity() < 0) {
                return false;
            }
            inventoryModel.setQuantity(inventoryModel.getQuantity() - purchase.getQuantity());
            purchaseRepository.deleteById(id);
            inventoryService.deleteFromInventory(id);
            return true;
        }
        return false;
    }

    @Override
    public PurchaseModel findPurchase(Long id) {
        return purchaseRepository.findById(id).orElse(null);
    }

    @Override
    public boolean editPurchase(PurchaseModel purchase) {
        InventoryModel inventoryModel = inventoryService.findInventoryByProduct(purchase.getIdeProduct());
        if (inventoryModel == null) {
            return false;
        }
        int quantity=0;
        if(inventoryModel.getQuantity()>purchase.getQuantity()){
            quantity=inventoryModel.getQuantity() - purchase.getQuantity();
        }else if(inventoryModel.getQuantity()<purchase.getQuantity()){
            quantity=purchase.getQuantity()-inventoryModel.getQuantity();
        }
        if (quantity < 0) {
            return false;
        }
        boolean saveInventory = inventoryService.editInventory(inventoryModel);
        if (saveInventory) {
            if (purchaseRepository.existsById(purchase.getIdePurchase())) {
                purchaseRepository.save(purchase);
                return true;
            }
        } else {
            return false;
        }
        return false;
    }
}
