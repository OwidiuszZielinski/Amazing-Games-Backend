package com.example.amazinggamesbackend.core.shoppingcart.dto;

import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;
import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartDTO {
    private int id;

    public static AddToCartDTO from(ShoppingCartEntity cart) {
        return AddToCartDTO.builder().build();

    }
}
