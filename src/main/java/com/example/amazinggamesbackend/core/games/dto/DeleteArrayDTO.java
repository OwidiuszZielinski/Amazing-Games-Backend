package com.example.amazinggamesbackend.core.games.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteArrayDTO {
    private List<Integer> ids;

    public static DeleteArrayDTO from() {
        return DeleteArrayDTO.builder()
                .build();
    }

}
