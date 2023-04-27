package com.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "order-service")
public class Order {
    @Id
    private String orderId;
    private String inventoryId;
    private int quantity;
    private double totalAmount;
    private String orderStatus;
    private Instant orderDate;
}
