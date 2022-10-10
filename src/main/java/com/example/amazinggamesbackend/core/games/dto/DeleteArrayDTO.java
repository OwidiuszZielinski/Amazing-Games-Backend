package com.example.amazinggamesbackend.core.games.dto;
import lombok.*;

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
