package com.udla.management.sales.controllers;

import java.util.List;

import com.udla.management.exceptions.ManagementException;
import com.udla.management.sales.models.SaleModel;
import com.udla.management.sales.services.SalesService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling sales operations.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/sales")
public class SalesController {

    private final SalesService salesService;

    /**
     * Retrieves all sales.
     *
     * @return list of sales
     */
    @GetMapping
    public ResponseEntity<List<SaleModel>> getAllSales() {
        return ResponseEntity.ok(salesService.getSales());
    }

    /**
     * Retrieves a sale by its ID.
     *
     * @param id sale identifier
     * @return sale data
     * @throws ManagementException if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<SaleModel> getSaleById(@PathVariable Long id) throws ManagementException {
        return ResponseEntity.ok(salesService.getSaleById(id));
    }

    /**
     * Creates a new sale.
     *
     * @param sale sale request body
     * @throws ManagementException if validation fails
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSale(@RequestBody @Valid SaleModel sale) throws ManagementException {
        salesService.createSale(sale);
    }

    /**
     * Updates an existing sale.
     *
     * @param sale updated sale data
     * @throws ManagementException if validation fails
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateSale(@RequestBody @Valid SaleModel sale) throws ManagementException {
        salesService.updateSale(sale);
    }

    /**
     * Deletes a sale.
     *
     * @param id sale identifier
     * @throws ManagementException if not found
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSale(@PathVariable Long id) throws ManagementException {
        salesService.deleteSale(id);
    }
}
