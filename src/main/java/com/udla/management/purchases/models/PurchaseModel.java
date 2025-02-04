package com.udla.management.purchases.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PurchaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idePurchase;
    private UUID ideSupplier;
    private UUID ideProduct;
    private int quantity;
}
