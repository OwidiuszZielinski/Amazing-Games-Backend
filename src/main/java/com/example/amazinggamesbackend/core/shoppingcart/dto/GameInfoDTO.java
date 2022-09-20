package com.example.amazinggamesbackend.core.shoppingcart.dto;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.shoppingcart.model.CartDetail;
import lombok.*;

import javax.persistence.Column;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameInfoDTO {
    private Integer id;
    private String title;
    private String type;
    private double price;
    private String description;
    private double rating;
    private boolean availability;

    public static GameInfoDTO from(GameEntity game){
        return GameInfoDTO.builder().id(game.getId())
                .title(game.getTitle())
                .type(game.getType())
                .price(game.getPrice())
                .description(game.getDescription())
                .rating(game.getRating())
                .availability(game.isAvailability())
                .build();
    }



}
