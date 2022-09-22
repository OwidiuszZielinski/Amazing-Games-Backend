package com.example.amazinggamesbackend.core.cart.dto;

import com.example.amazinggamesbackend.core.cart.model.CartEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCartDTO {
    private int userID;
    public CreateCartDTO from (CartEntity cart){
        return CreateCartDTO.builder().build();
    }
}
