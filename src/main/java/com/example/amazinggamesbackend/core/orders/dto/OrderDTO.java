package com.example.amazinggamesbackend.core.orders.dto;

import com.example.amazinggamesbackend.core.games.model.Game;
import com.example.amazinggamesbackend.core.orders.model.Order;
import com.example.amazinggamesbackend.core.orders.model.OrderStatus;
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
    private OrderStatus status;
    private String date;
    private double value;
    private double valueWithTax;



    public static OrderDTO from(Order order){
        return OrderDTO.builder()
                .id(order.getId())
                .user(order.getUser().getId())
                .games(order.getGames().stream().mapToInt(Game::getId).boxed().toList())
                .status(order.getStatus())
                .date(order.getDate())
                .value(roundToTwoDecimalPlaces(order.getValue()))
                .build();
    }
    public static double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

}
