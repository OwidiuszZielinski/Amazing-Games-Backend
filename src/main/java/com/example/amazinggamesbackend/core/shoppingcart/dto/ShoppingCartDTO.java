package com.example.amazinggamesbackend.core.shoppingcart.dto;

import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import lombok.*;


import java.util.List;

@Getter
@Setter
@Builder
public class ShoppingCartDTO {

    private int id;
    private List<CartDetailInfoDTO> cartDetailsinfo;
    private UserEntity user;

    public static ShoppingCartDTO from(ShoppingCartEntity shoppingCart){
        return ShoppingCartDTO.builder()
                .id(shoppingCart.getId())
                .cartDetailsinfo(CartDetailInfoDTO.fromList(shoppingCart.getCartDetails()))
                .user(shoppingCart.getUser()).build();

    }
}
