package com.orderservice.controller;

import com.orderservice.model.OrderRequest;
import com.orderservice.model.OrderResponse;
import com.orderservice.model.OrderUpdate;
import com.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/placeOrder")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.FOUND)
    public OrderResponse getOrderDetails(@PathVariable String orderId) {
        return orderService.getOrderDetails(orderId);
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateOrder(@PathVariable String orderId, @RequestBody OrderUpdate orderUpdate) {
        orderService.updateOrder(orderId, orderUpdate);
    }


}
