package com.example.amazinggamesbackend.core.games.dto;

import com.example.amazinggamesbackend.core.games.model.GamesEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GamesDTO {

    private String title;
    private String type;
    private double price;
    private String description;
    private double rating;
    private boolean availability;


    public static GamesDTO from (GamesEntity gameEntity){
        return GamesDTO.builder()
                .availability(gameEntity.isAvailability())
                .build();
    }
}
