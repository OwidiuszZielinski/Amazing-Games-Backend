package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.games.exceptions.GameNotFound;
import com.example.amazinggamesbackend.core.orders.OrderRepository;
import com.example.amazinggamesbackend.core.orders.OrderService;
import com.example.amazinggamesbackend.core.orders.dto.CreateOrderDTO;
import com.example.amazinggamesbackend.core.orders.dto.EditOrderDTO;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import com.example.amazinggamesbackend.exceptions.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

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
@RequestMapping("/orders/")
public class OrderController {

    @ExceptionHandler({ IllegalArgumentException.class, NoSuchElementException.class })
    public ResponseEntity<ErrorResponse> handleException(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    private final OrderService orderService;

    @Operation(summary = "Create new order")
    @PostMapping
    public ResponseEntity<CreateOrderDTO> newOrder(@RequestBody CreateOrderDTO order) {
        CreateOrderDTO create = orderService.createOrder(order);
        return new ResponseEntity<>(create,HttpStatus.CREATED);

    }

    @Operation(summary = "Get all orders")
    @GetMapping
    public List<OrderDTO> getOrders() {
        return orderService.getAllOrders();
    }

    @Operation(summary = "Delete order")
    @DeleteMapping
    public void deleteOrders(@RequestBody List<Integer> gameIds) {
        orderService.deleteOrders(gameIds);
    }

    @Operation(summary = "Edit order by id")
    @PatchMapping("/{orderId}")
    public ResponseEntity<EditOrderDTO> editOrder(@PathVariable int orderId ,@RequestBody EditOrderDTO order) {
        EditOrderDTO edited = orderService.updateOrder(orderId, order);
        return new ResponseEntity<>(edited, HttpStatus.ACCEPTED);
    }

}
