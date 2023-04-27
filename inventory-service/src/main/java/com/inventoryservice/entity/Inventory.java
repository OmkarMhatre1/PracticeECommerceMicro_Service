package com.inventoryservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "inventory-service")
public class Inventory {
    @Id
    private String inventoryId;
    private String name;
    private int quantity;
    private double price;
    private String review;
}
