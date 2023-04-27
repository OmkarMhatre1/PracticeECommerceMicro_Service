package com.inventoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryUpdate {
    private String inventoryId;
    private String name;
    private int quantity;
    private double price;
    private String review;
}
