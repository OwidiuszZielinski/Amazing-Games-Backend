package com.example.amazinggamesbackend.core.cart.dto;

import com.example.amazinggamesbackend.core.cart.model.Cart;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CartDTO {

    private int id;
    private List<CartDetailDTO> cartDetailsInfo;
    private UserDTO user;

    public static CartDTO from(Cart shoppingCart){
        return CartDTO.builder()
                .id(shoppingCart.getId())
                .cartDetailsInfo(CartDetailDTO.fromList(shoppingCart.getCartDetails()))
                .user(UserDTO.fromWithoutPassword(shoppingCart.getUser())).build();

    }
}
