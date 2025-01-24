package com.udla.management.purchases.services;

import com.udla.management.purchases.models.PurchaseModel;

import java.util.List;

public interface IPurchaseService {

    public List<PurchaseModel> getPurchases();

    public boolean savePurchase(PurchaseModel purchase);

    public boolean deletePurchase(Long id);

    public PurchaseModel findPurchase(Long id);

    public boolean editPurchase(PurchaseModel purchase);
}
