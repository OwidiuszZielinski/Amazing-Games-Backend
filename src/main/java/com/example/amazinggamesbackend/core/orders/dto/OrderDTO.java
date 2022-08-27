package com.example.amazinggamesbackend.core.orders.dto;

import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long user;
    private ArrayList<Integer> games;
    private int status;
    private String date;


    public static OrderDTO from (OrderEntity order){
        return OrderDTO.builder()
                .date(OrderEntity.orderdate())
                .build();
    }


}
