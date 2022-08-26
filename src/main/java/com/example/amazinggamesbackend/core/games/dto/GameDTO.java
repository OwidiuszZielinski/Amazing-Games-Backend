package com.example.amazinggamesbackend.core.games.dto;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameDTO {

    private String title;
    private String type;
    private double price;
    private String description;
    private double rating;
    private boolean availability;


    public static GameDTO from (GameEntity gameEntity){
        return GameDTO.builder()
                .availability(gameEntity.isAvailability())
                .build();
    }
}
