package com.example.amazinggamesbackend.core.games.dto;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteGamesDTO {

    private List<Integer> ids;

    public static DeleteGamesDTO from(GameEntity game) {
        return DeleteGamesDTO.builder().build();

    }

}
