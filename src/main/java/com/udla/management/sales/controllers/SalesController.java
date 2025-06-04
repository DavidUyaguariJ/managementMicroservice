package com.udla.management.sales.controllers;


import com.udla.management.exceptions.ManagementException;
import com.udla.management.sales.models.SaleModel;
import com.udla.management.sales.services.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path="/sales")
public class SalesController {

    private final SalesService salesService;

    @GetMapping
    public ResponseEntity<List<SaleModel>> getAllSales() {
        return ResponseEntity.ok(salesService.getSales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleModel> getSaleById(@PathVariable Long id) throws ManagementException {
        return ResponseEntity.ok(salesService.getSaleById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSale(@RequestBody @Valid final SaleModel sale) throws ManagementException {
        salesService.createSale(sale);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateSale(@RequestBody @Valid final SaleModel sale) throws ManagementException {
        salesService.updateSale(sale);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSale(@PathVariable Long id) throws ManagementException {
        salesService.deleteSale(id);
    }

}
