package com.orderservice.service;

import com.orderservice.entity.Order;
import com.orderservice.feignClient.InventoryService;
import com.orderservice.model.*;
import com.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private RestTemplate restTemplate;

    public String placeOrder(OrderRequest orderRequest) {

        log.info("Placing Order Request :{}", orderRequest.getInventoryId());
        //feign Client
        inventoryService.reduceQuantity(orderRequest.getInventoryId(), orderRequest.getQuantity());

        log.info("Crating order with status CREATED");

//        InventoryRequest inventoryRequest = webClientBuilder.build()
//                .post()
//                .uri("http://INVENTORY-SERVICE/inventory/{inventoryId}/{quantity}",orderRequest.getInventoryId())
//                .retrieve()
//                .bodyToMono(InventoryRequest.class)
//                .block();

//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://INVENTORY-SERVICE/inventory/";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        restTemplate.exchange(url, HttpMethod.POST, entity, InventoryRequest.class, orderRequest.getInventoryId(), orderRequest.getQuantity());


        Order order = Order.builder()
                .inventoryId(orderRequest.getInventoryId())
                .quantity(orderRequest.getQuantity())
                .totalAmount(orderRequest.getTotalAmount())
                .orderStatus("PLACED")
                .orderDate(Instant.now())
                .build();
        order = orderRepository.save(order);
        return order.getOrderId();
    }


    public OrderResponse getOrderDetails(String orderId) {
        Order order=orderRepository.findById(orderId).get();

       // InventoryResponse inventoryResponse = inventoryService.getProductById(order.getInventoryId());

       //InventoryResponse inventoryResponse = restTemplate.getForObject("http://INVENTORY-SERVICE/inventory/"+order.getInventoryId(),InventoryResponse.class);

        InventoryResponse inventoryResponse = webClientBuilder.build()
                .get()
                .uri("http://INVENTORY-SERVICE/inventory/" + order.getInventoryId())
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .block();

        OrderResponse.InventoryDetails inventoryDetails=OrderResponse.InventoryDetails.builder()
                .inventoryId(inventoryResponse.getInventoryId())
                .name(inventoryResponse.getName())
                .quantity(inventoryResponse.getQuantity())
                .price(inventoryResponse.getPrice())
                .review(inventoryResponse.getReview())
                .build();

        OrderResponse orderResponse= OrderResponse.builder()
                .orderId(order.getOrderId())
                .inventoryId(order.getInventoryId())
                .quantity(order.getQuantity())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .inventoryDetails(inventoryDetails)
                .build();

        return orderResponse;

    }

    public void updateOrder(String orderId, OrderUpdate orderUpdate) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()){
            Order order = optionalOrder.get();

            order.setQuantity(orderUpdate.getQuantity());
            order.setTotalAmount(orderUpdate.getTotalAmount());

            orderRepository.save(order);
        }
    }
}
