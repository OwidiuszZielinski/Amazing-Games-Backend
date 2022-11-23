package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.games.dto.GameEntityDTO;
import com.example.amazinggamesbackend.core.orders.OrderRepository;
import com.example.amazinggamesbackend.core.orders.OrderService;
import com.example.amazinggamesbackend.core.orders.dto.CreateOrderDTO;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class OrderController {


    private final OrderRepository orderRepository;

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderRepository orderRepository ,OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @Operation(summary = "Create new order")
    @PostMapping("/orders")
    public ResponseEntity newOrder(@RequestBody CreateOrderDTO order) {
        if (order.getGames().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            orderService.createOrder(order);
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Get all orders")
    @GetMapping("/orders")
    public List<OrderDTO> getOrders() {
        return orderService.getAllOrders();
    }

    @Operation(summary = "Delete order")
    @DeleteMapping("/orders")
    public ResponseEntity deleteOrders(@RequestBody List<Integer> ids) {
        if (ids.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        if (orderRepository.findAllById(ids).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        orderService.deleteOrders(ids);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Edit order by id")
    @PatchMapping("/orders/{Id}")
    public void editOrder(@PathVariable int Id ,@RequestBody CreateOrderDTO order) {
        orderService.updateOrder(Id ,order);

    }

    @Operation(summary = "Bestseller")
    @GetMapping("orders/bestseller")
    public GameEntityDTO getBestseller() {
        return orderService.bestseller();
    }
}
