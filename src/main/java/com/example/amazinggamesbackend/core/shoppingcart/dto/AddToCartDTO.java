package com.example.amazinggamesbackend.core.shoppingcart.dto;

import com.example.amazinggamesbackend.core.shoppingcart.model.CartDetail;
import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;
import io.swagger.models.auth.In;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartDTO {
    private int gameId;
    private int userId;
    private CartDetail cartDetail;

    public static AddToCartDTO from(ShoppingCartEntity cart) {
        return AddToCartDTO.builder().build();

    }
}
