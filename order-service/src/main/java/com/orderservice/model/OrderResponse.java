package com.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private String orderId;
    private String inventoryId;
    private int quantity;
    private double totalAmount;
    private String orderStatus;
    private Instant orderDate;
    private InventoryDetails inventoryDetails;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class InventoryDetails {
        private String inventoryId;
        private String name;
        private int quantity;
        private double price;
        private String review;
    }

}
