package com.example.amazinggamesbackend.core.orders.dto;

import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteOrderDTO {
    private List<Integer> ids;

    public static DeleteOrderDTO from(OrderEntity order) {
        return DeleteOrderDTO.builder()
                .build();
    }
}