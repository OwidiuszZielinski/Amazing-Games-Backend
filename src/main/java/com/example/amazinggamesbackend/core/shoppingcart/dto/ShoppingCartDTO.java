package com.example.amazinggamesbackend.core.shoppingcart.dto;

import com.example.amazinggamesbackend.core.shoppingcart.model.CartDetail;
import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartDTO {

    private int id;
    private List<CartDetail> cartDetails;
    private UserEntity user;

    public static ShoppingCartDTO from(ShoppingCartEntity shoppingCart){
        return  ShoppingCartDTO.builder().build();

    }
}
