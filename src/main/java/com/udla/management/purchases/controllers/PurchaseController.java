package com.udla.management.purchases.controllers;

import com.udla.management.purchases.models.PurchaseModel;
import com.udla.management.purchases.services.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path="/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping(path ="/getPurchases")
    public List<PurchaseModel> getPurchases() {
        return purchaseService.getPurchases();
    }

    @GetMapping(path = "/getPurchaseById/{id}")
    public ResponseEntity<PurchaseModel> getPurchaseById(@PathVariable Long id) {
        if(purchaseService.findPurchase(id) == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(purchaseService.findPurchase(id), HttpStatus.OK);
    }

    @PostMapping(path ="/addPurchase")
    public ResponseEntity<PurchaseModel> addPurchase(@RequestBody PurchaseModel purchase) {
        boolean response = purchaseService.savePurchase(purchase);
        if(!response) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(purchase, HttpStatus.CREATED);
    }

    @PutMapping(path="/editPurchase")
    public ResponseEntity<PurchaseModel> editPurchase(@RequestBody PurchaseModel purchase) {
        boolean response = purchaseService.editPurchase(purchase);
        if(!response) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(purchase, HttpStatus.OK);
    }

    @DeleteMapping(path="/deletePurchase/{id}")
    public ResponseEntity<PurchaseModel> deletePurchase(@PathVariable Long id) {
        boolean response = purchaseService.deletePurchase(id);
        if(!response) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(purchaseService.findPurchase(id), HttpStatus.OK);
    }
}
