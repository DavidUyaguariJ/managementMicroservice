package com.udla.management.sales.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SaleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ide_sale", nullable = false, updatable = false)
    private Long ideSale;

    @NotNull(message = "La descripcion no debe ser nula")
    private String description;

    @NotNull(message = "la cantidad no puede ser nula")
    private Integer quantity;

}
