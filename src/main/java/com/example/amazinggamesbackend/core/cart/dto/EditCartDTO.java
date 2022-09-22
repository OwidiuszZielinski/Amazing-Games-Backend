package com.example.amazinggamesbackend.core.cart.dto;

import com.example.amazinggamesbackend.core.cart.model.CartEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditCartDTO {

    private List<Integer> ids;

    public static EditCartDTO from(CartEntity cart) {
        return EditCartDTO.builder().build();

    }

}
