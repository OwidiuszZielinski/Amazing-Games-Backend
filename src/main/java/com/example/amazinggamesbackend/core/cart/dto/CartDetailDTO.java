package com.example.amazinggamesbackend.core.cart.dto;

import com.example.amazinggamesbackend.core.cart.model.CartDetail;
import com.example.amazinggamesbackend.core.games.dto.GameEntityDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetailDTO {

    private GameEntityDTO game;
    private int quantity;

    public static CartDetailDTO from(CartDetail cartDetail) {
        return CartDetailDTO.builder().quantity(cartDetail.getQuantity()).game(GameEntityDTO.from(cartDetail.getGame())).build();
    }


    public static List<CartDetailDTO> fromList(List<CartDetail> cartDetail) {
        List<CartDetailDTO> templist = new ArrayList<>();
        for (CartDetail x : cartDetail) {
            templist.add(from(x));
        }
        return templist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDetailDTO that = (CartDetailDTO) o;
        return quantity == that.quantity && Objects.equals(game ,that.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game ,quantity);
    }
}
