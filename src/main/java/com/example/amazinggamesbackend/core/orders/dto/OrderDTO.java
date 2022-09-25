package com.example.amazinggamesbackend.core.orders.dto;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private int id;
    private int user;
    private List<Integer> games;
    private int status;
    private String date;
    private double value;
    private double valueWithTax;



    public static OrderDTO from(OrderEntity order){
        return OrderDTO.builder()
                .id(order.getId())
                .user(order.getUser().getId())
                .games(order.getGames().stream().mapToInt(GameEntity::getId).boxed().toList())
                .status(order.getStatus())
                .date(order.getDate())
                .value(order.getValue())
                .build();
    }


}
