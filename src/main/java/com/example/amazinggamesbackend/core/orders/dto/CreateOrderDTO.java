package com.example.amazinggamesbackend.core.orders.dto;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.example.amazinggamesbackend.core.orders.model.OrderStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderDTO {


    private int user;
    private List<Integer> games;
    private OrderStatus status;


    public static CreateOrderDTO from(OrderEntity order) {
        return CreateOrderDTO.builder()
                .user(order.getUser().getId())
                .games(order.getGames().stream().mapToInt(GameEntity::getId).boxed().toList())
                .build();
    }
}