package com.orderservice.feignClient;

import com.orderservice.model.InventoryRequest;
import com.orderservice.model.InventoryResponse;
import com.orderservice.model.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "INVENTORY-SERVICE/inventory")
public interface InventoryService {

    @PutMapping("/{inventoryId}/{quantity}")
    @ResponseStatus(HttpStatus.OK)
    void reduceQuantity(@PathVariable String inventoryId, @PathVariable int quantity);

    @GetMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.FOUND)
    InventoryResponse getProductById(@PathVariable String inventoryId);


}
