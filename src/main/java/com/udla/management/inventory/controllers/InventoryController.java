package com.udla.management.inventory.controllers;

import com.udla.management.inventory.models.InventoryModel;
import com.udla.management.inventory.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path="/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/getInventory")
    public List<InventoryModel> getInventory() {
        return inventoryService.getInventory();
    }

    @GetMapping(path = "/getInventoryById/{id}")
    public ResponseEntity<InventoryModel> getInventoryById(@PathVariable Long id) {
        if(inventoryService.findOnInventory(id)==null) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(inventoryService.findOnInventory(id),HttpStatus.OK);
    }

    @PostMapping(path = "/insertToInventory")
    public ResponseEntity<InventoryModel> insertToInventory(@RequestBody InventoryModel inventory) {
        boolean response = inventoryService.saveToInventory(inventory);
        if(!response) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(inventory,HttpStatus.CREATED);
    }

    @PutMapping(path = "/editFromInvetory")
    public ResponseEntity<InventoryModel> editFromInventory(@RequestBody InventoryModel inventory) {
        boolean response = inventoryService.editInventory(inventory);
        if(!response) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(inventory,HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteFromInventory/{id}")
    public ResponseEntity<InventoryModel> deleteFromInventory(@PathVariable Long id) {
        boolean response= inventoryService.deleteFromInventory(id);
        if(!response) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(inventoryService.findOnInventory(id),HttpStatus.OK);
    }
}
