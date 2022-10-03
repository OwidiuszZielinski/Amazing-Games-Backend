package com.example.amazinggamesbackend.core.games.dto;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameEntityDTO {
    private Integer id;
    private String title;
    private String type;
    private double price;
    private String description;
    private double rating;
    private boolean availability;

    public static GameEntityDTO from(GameEntity game){
        return GameEntityDTO.builder().id(game.getId())
                .title(game.getTitle())
                .type(game.getType())
                .price(game.getPrice())
                .description(game.getDescription())
                .rating(game.getRating())
                .availability(game.isAvailability())
                .build();
    }


}
