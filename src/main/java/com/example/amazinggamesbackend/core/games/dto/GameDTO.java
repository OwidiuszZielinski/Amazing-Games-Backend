package com.example.amazinggamesbackend.core.games.dto;

import com.example.amazinggamesbackend.core.games.model.Game;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameDTO {
    private Integer id;
    private String title;
    private String type;
    private double price;
    private String description;
    private double rating;
    private boolean availability;


    public static GameDTO from(Game game) {
        return GameDTO.builder().id(game.getId())
                .title(game.getTitle())
                .type(game.getType())
                .price(game.getPrice())
                .description(game.getDescription())
                .rating(game.getRating())
                .availability(game.isAvailability())
                .build();
    }

    public static Game to(GameDTO gameDTO) {
        Game game = new Game();
        game.setId(gameDTO.getId());
        game.setTitle(gameDTO.getTitle());
        game.setType(gameDTO.getType());
        game.setPrice(gameDTO.getPrice());
        game.setDescription(gameDTO.getDescription());
        game.setRating(gameDTO.getRating());
        game.setAvailability(gameDTO.isAvailability());
        return game;
    }

}
