package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.orders.OrderService;
import com.example.amazinggamesbackend.core.orders.dto.OrdersDTO;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @Operation(summary = "Create new order")
    @PostMapping("/orders")
    public ResponseEntity newOrder(@RequestBody OrdersDTO order) {
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
    @Operation(summary ="Delete order by id")
    @DeleteMapping("/orders/{Id}")
    public ResponseEntity deleteOrder(@PathVariable int Id){
        orderService.deleteOrder(Id);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Edit order by id")
    @PatchMapping("/orders/{Id}")
    public OrderEntity editOrder(@PathVariable int Id,@RequestBody OrdersDTO order){
       return orderService.editOrder(Id,order);

    }
}
