package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.orders.OrderService;
import com.example.amazinggamesbackend.core.orders.dto.OrdersDTO;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @Operation(summary = "Create new order")
    @PostMapping("/orders")
    public ResponseEntity neworder(@RequestBody OrdersDTO order) {
        if (order.getGames().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            orderService.addorder(order);
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Get all orders")
    @GetMapping("/orders")
    public List<OrderEntity> getOrders() {
        return orderService.getAllOrders();
    }
}
