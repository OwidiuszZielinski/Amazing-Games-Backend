package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.orders.OrdersService;
import com.example.amazinggamesbackend.core.orders.dto.DeleteOrderDTO;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import io.swagger.models.auth.In;
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
    OrdersService ordersService;

    @Operation(summary = "Create new order")
    @PostMapping("/orders")
    public ResponseEntity newOrder(@RequestBody OrderDTO order) {
        if (order.getGames().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            ordersService.addorder(order);
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Get all orders")
    @GetMapping("/orders")
    public List<OrderEntity> getOrders() {
        return ordersService.getAllOrders();
    }

    @Operation(summary ="Delete order by id/ids")
    @DeleteMapping("/orders")
    public ResponseEntity deleteOrders(@RequestBody DeleteOrderDTO deleteOrderDTO){
        if(deleteOrderDTO.getIds().isEmpty()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        System.out.println(deleteOrderDTO.getIds());
        ordersService.deleteOrders(deleteOrderDTO.getIds());
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Edit order by id")
    @PatchMapping("/orders/{Id}")
    public OrderEntity editOrder(@PathVariable int Id,@RequestBody OrderDTO order){
       return ordersService.editOrder(Id,order);

    }
}
