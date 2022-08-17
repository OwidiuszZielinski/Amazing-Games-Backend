package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.orders.OrderService;
import com.example.amazinggamesbackend.core.orders.dto.OrdersDTO;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class OrderController {
    OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }



    @Operation(summary = "Create new order")
    @PostMapping("/orders")
    public OrderEntity neworder(@RequestBody OrdersDTO order){
        return orderService.addorder(order);
    }

    @Operation(summary = "Get all orders")
    @GetMapping("/orders")
    public List<OrderEntity> getOrders(){
        return orderService.getAllOrders();
    }
}
