package com.inventoryservice.service;

import com.inventoryservice.entity.Inventory;
import com.inventoryservice.model.InventoryRequest;
import com.inventoryservice.model.InventoryResponse;
import com.inventoryservice.model.InventoryUpdate;
import com.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    //add
    public void addProduct(InventoryRequest inventoryRequest) {
        Inventory inventory = Inventory.builder()
                .name(inventoryRequest.getName())
                .quantity(inventoryRequest.getQuantity())
                .price(inventoryRequest.getPrice())
                .review(inventoryRequest.getReview())
                .build();
        inventoryRepository.save(inventory);
        log.info("Product {} is saved", inventory.getInventoryId());

    }

    //getAll
    public List<InventoryResponse> getAllProducts() {
        List<Inventory> inventoryList = inventoryRepository.findAll();
        return inventoryList.stream().map(this::mapToInventoryResponse).collect(Collectors.toList());
    }

    private InventoryResponse mapToInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .inventoryId(inventory.getInventoryId())
                .name(inventory.getName())
                .quantity(inventory.getQuantity())
                .price(inventory.getPrice())
                .review(inventory.getReview())
                .build();
    }

    //getById
    public InventoryResponse findByProductId(String inventoryId) {
        InventoryResponse inventoryResponse = inventoryRepository.findById(inventoryId).map(this::mapToInventoryResponse).get();
        log.info("Product with {} give id is found", inventoryResponse.getInventoryId());
        return inventoryResponse;
    }


    //update
    public void updateInventory(String inventoryId, InventoryUpdate inventoryUpdate)  {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(inventoryId);

        if (optionalInventory.isPresent()) {
            Inventory inventory = optionalInventory.get();

            // Update the inventory with the new information
            inventory.setName(inventoryUpdate.getName());
            inventory.setQuantity(inventoryUpdate.getQuantity());
            inventory.setPrice(inventoryUpdate.getPrice());
            inventory.setReview(inventoryUpdate.getReview());

            // Save the updated inventory
            inventoryRepository.save(inventory);
        } else {
            //throw new InventoryNotFoundException("Inventory not found with id: " + inventoryId);
        }
    }

    //delete
    public void deleteProduct(String inventoryId) {
        inventoryRepository.deleteById(inventoryId);
    }

    //reduce
    public void reduceQuantity(String inventoryId, int quantity) throws ProductQuantityException {
        Inventory inventory = inventoryRepository.findById(inventoryId).get();

        if(inventory.getQuantity()<quantity){
            throw new ProductQuantityException("Product does not have sufficient quantity");
        }
        else {
            inventory.setQuantity(inventory.getQuantity()-quantity);
            inventoryRepository.save(inventory);
        }
    }



//    public void updateInventory(String inventoryId, InventoryUpdate inventoryUpdate) {
//        inventoryRepository.findById(inventoryId)
//                .map(inventory -> inventory.builder()
//                        .name(inventoryUpdate.getName())
//                        .quantity(inventoryUpdate.getQuantity())
//                        .price(inventoryUpdate.getPrice())
//                        .review(inventoryUpdate.getReview())
//                        .build())
//                .ifPresent(inventoryRepository::save);
//    }



}
