package com.example.amazinggamesbackend.core.orders.dto;

import com.example.amazinggamesbackend.core.games.model.Game;
import com.example.amazinggamesbackend.core.orders.model.Order;
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


    public static CreateOrderDTO from(Order order) {
        return CreateOrderDTO.builder()
                .user(order.getUser().getId())
                .games(order.getGames().stream().mapToInt(Game::getId).boxed().toList())
                .build();
    }
}