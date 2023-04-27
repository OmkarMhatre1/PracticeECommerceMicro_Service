package com.inventoryservice.controller;

import com.inventoryservice.model.InventoryRequest;
import com.inventoryservice.model.InventoryResponse;
import com.inventoryservice.model.InventoryUpdate;
import com.inventoryservice.service.InventoryService;
import com.inventoryservice.service.ProductQuantityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody InventoryRequest inventoryRequest) {
        inventoryService.addProduct(inventoryRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<InventoryResponse> getAllProducts() {
        return inventoryService.getAllProducts();
    }

    @GetMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.FOUND)
    public InventoryResponse getProductById(@PathVariable String inventoryId) {
        return inventoryService.findByProductId(inventoryId);
    }

    @PutMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateInventory(@PathVariable String inventoryId,
                                @RequestBody InventoryUpdate inventoryUpdate) {
        inventoryService.updateInventory(inventoryId, inventoryUpdate);
    }

    @DeleteMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteProduct(@PathVariable String inventoryId) {
        inventoryService.deleteProduct(inventoryId);
        return "Product Deleted";
    }

    //reduceQuantity
    @PutMapping("/{inventoryId}/{quantity}")
    @ResponseStatus(HttpStatus.OK)
    public void reduceQuantity(@PathVariable String inventoryId, @PathVariable int quantity) throws ProductQuantityException {
        inventoryService.reduceQuantity(inventoryId, quantity);
    }




}
