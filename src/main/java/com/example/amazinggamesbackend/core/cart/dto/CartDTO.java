package com.example.amazinggamesbackend.core.cart.dto;

import com.example.amazinggamesbackend.core.cart.model.CartEntity;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import lombok.*;


import java.util.List;

@Getter
@Setter
@Builder
public class CartDTO {

    private int id;
    private List<CartDetailDTO> cartDetailsinfo;
    private UserEntity user;

    public static CartDTO from(CartEntity shoppingCart){
        return CartDTO.builder()
                .id(shoppingCart.getId())
                .cartDetailsinfo(CartDetailDTO.fromList(shoppingCart.getCartDetails()))
                .user(shoppingCart.getUser()).build();

    }
}