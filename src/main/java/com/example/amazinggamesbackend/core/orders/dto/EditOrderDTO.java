package com.example.amazinggamesbackend.core.orders.dto;

import com.example.amazinggamesbackend.core.orders.model.OrderStatus;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditOrderDTO {

    private List<Integer> games;
    private OrderStatus status;



}
