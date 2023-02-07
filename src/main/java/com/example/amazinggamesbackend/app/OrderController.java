package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.orders.OrderRepository;
import com.example.amazinggamesbackend.core.orders.OrderService;
import com.example.amazinggamesbackend.core.orders.dto.CreateOrderDTO;
import com.example.amazinggamesbackend.core.orders.dto.EditOrderDTO;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
public class OrderController {


    private final OrderService orderService;



    @Operation(summary = "Create new order")
    @PostMapping("/orders")
    public ResponseEntity<CreateOrderDTO> newOrder(@RequestBody CreateOrderDTO order) {
        try {
            orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Operation(summary = "Get all orders")
    @GetMapping("/orders")
    public List<OrderDTO> getOrders() {
        return orderService.getAllOrders();
    }

    @Operation(summary = "Delete order")
    @DeleteMapping("/orders")
    public ResponseEntity<Integer> deleteOrders(@RequestBody List<Integer> ids) {
        try {
            orderService.deleteOrders(ids);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Operation(summary = "Edit order by id")
    @PatchMapping("/orders/{Id}")
    public ResponseEntity<EditOrderDTO> editOrder(@PathVariable int Id ,@RequestBody EditOrderDTO order) {
        try {
            orderService.updateOrder(Id ,order);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

    }


}
