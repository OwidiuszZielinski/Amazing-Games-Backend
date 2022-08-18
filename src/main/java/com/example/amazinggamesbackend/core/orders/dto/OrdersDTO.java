package com.example.amazinggamesbackend.core.orders.dto;

import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.example.amazinggamesbackend.core.users.dto.UsersDTO;
import com.example.amazinggamesbackend.core.users.model.UsersEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersDTO {

    private Integer user;
    private List<Integer> games;
    private String status;
    private String date;
    private double value;

    public static OrdersDTO from (OrderEntity order){
        return OrdersDTO.builder()
                .date(OrderEntity.orderdate())
                .build();
    }


}
