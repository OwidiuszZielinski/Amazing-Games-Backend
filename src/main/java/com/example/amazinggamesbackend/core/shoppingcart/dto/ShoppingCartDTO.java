package com.example.amazinggamesbackend.core.shoppingcart.dto;

import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartDTO {
    private int userID;
    private ArrayList<Integer> gamesIDS;


    public ShoppingCartDTO from (ShoppingCartEntity basket){
        return ShoppingCartDTO.builder().build();
    }
}
