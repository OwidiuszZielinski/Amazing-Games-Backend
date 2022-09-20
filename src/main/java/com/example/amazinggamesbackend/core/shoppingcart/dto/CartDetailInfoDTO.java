package com.example.amazinggamesbackend.core.shoppingcart.dto;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.shoppingcart.model.CartDetail;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetailInfoDTO {

    private GameInfoDTO game;
    private int quantity;

    public static CartDetailInfoDTO from(CartDetail cartDetail){
        return CartDetailInfoDTO.builder().quantity(cartDetail.getQuantity()).game(GameInfoDTO.from(cartDetail.getGame())).build();
    }
    public static List<CartDetailInfoDTO> fromList(List<CartDetail> cartDetail) {
        List<CartDetailInfoDTO> templist = new ArrayList<>();
        for (CartDetail x : cartDetail) {
            templist.add(from(x));

        }
        return templist;
    }

}
