package com.udla.management.sales.controllers;


import com.udla.management.sales.models.SaleModel;
import com.udla.management.sales.services.SalesService;
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

    @GetMapping(path ="/getSales")
    public List<SaleModel> getSales() {
        return salesService.getSales();
    }

    @GetMapping(path = "/getSaleById/{id}")
    public ResponseEntity<SaleModel> getSaleById(@PathVariable Long id) {
        if(salesService.findSale(id)==null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(salesService.findSale(id),HttpStatus.OK);
    }

    @PostMapping(path ="/addSale")
    public ResponseEntity<SaleModel> addSale(@RequestBody SaleModel sale){
        boolean response = salesService.saveSale(sale);
        if(!response) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(sale,HttpStatus.CREATED);
    }

    @PutMapping(path="/editSale")
    public ResponseEntity<SaleModel> editSale(@RequestBody SaleModel sale){
        boolean response = salesService.editSale(sale);
        if(!response) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(sale,HttpStatus.OK);
    }

    @DeleteMapping(path="deleteSale/{id}")
    public ResponseEntity<SaleModel> deleteSale(@PathVariable Long id){
        boolean response= salesService.deleteSale(id);
        if(!response) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(salesService.findSale(id),HttpStatus.OK);
    }

}
