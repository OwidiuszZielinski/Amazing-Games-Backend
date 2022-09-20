package com.example.amazinggamesbackend.core.shoppingcart.dto;

import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateShoppingCartDTO {
    private int userID;
    public CreateShoppingCartDTO from (ShoppingCartEntity cart){
        return CreateShoppingCartDTO.builder().build();
    }
}
