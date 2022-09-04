package com.example.amazinggamesbackend.core.shoppingcart.dto;

import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditShoppingCartDTO {

    private List<Integer> ids;

    public static EditShoppingCartDTO from(ShoppingCartEntity basket) {
        return EditShoppingCartDTO.builder().build();

    }

}
