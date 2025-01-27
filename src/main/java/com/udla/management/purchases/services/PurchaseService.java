package com.udla.management.purchases.services;

import com.udla.management.purchases.models.PurchaseModel;
import com.udla.management.purchases.repositories.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService implements IPurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Override
    public List<PurchaseModel> getPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public boolean savePurchase(PurchaseModel purchase) {
            purchaseRepository.save(purchase);
            return true;
    }

    @Override
    public boolean deletePurchase(Long id) {
        if (purchaseRepository.existsById(id)) {
            purchaseRepository.deleteById(id);
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
        int quantity=0;
        if (quantity < 0) {
            return false;
        }
         if (purchaseRepository.existsById(purchase.getIdePurchase())) {
            purchaseRepository.save(purchase);
            return true;
           }
        return false;
    }
}
