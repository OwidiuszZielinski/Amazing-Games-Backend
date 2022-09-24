package com.example.amazinggamesbackend.core.orders.dto;

import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.example.amazinggamesbackend.core.users.UsersService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    public static OrderDTO from(OrderEntity order){
        return OrderDTO.builder()
                .id(order.getId())
                .user(order.getUser().getId())
                .games(order.getGamesEntities().stream().mapToInt(GameEntity::getId).boxed().toList())
                .status(order.getStatus())
                .date(order.getDate())
                .value(order.getValue())
                .build();
    }


}
