package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.games.dto.GameEntityDTO;
import com.example.amazinggamesbackend.core.orders.OrdersRepository;
import com.example.amazinggamesbackend.core.orders.OrdersService;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrdersController {

    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrdersService ordersService;

    @Operation(summary = "Create new order")
    @PostMapping("/orders")
    public ResponseEntity newOrder(@RequestBody OrderDTO order) {
        if (order.getGames().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            ordersService.createOrder(order);
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Get all orders")
    @GetMapping("/orders")
    public List<OrderDTO> getOrders() {
        return ordersService.getAllOrders();
    }

    @Operation(summary = "Delete order")
    @DeleteMapping("/orders")
    public ResponseEntity deleteOrders(@RequestBody List<Integer> ids) {
        if (ids.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        if (ordersRepository.findAllById(ids).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        ordersService.deleteOrders(ids);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Edit order by id")
    @PatchMapping("/orders/{Id}")
    public void editOrder(@PathVariable int Id ,@RequestBody OrderDTO order) {
        ordersService.updateOrder(Id ,order);

    }

    @Operation(summary = "Bestseller")
    @GetMapping("orders/bestseller")
    public GameEntityDTO getBestseller() {
        return ordersService.bestseller();
    }
}
