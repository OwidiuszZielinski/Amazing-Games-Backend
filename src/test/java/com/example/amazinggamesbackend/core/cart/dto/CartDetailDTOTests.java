package com.example.amazinggamesbackend.core.cart.dto;

import com.example.amazinggamesbackend.core.cart.model.CartDetail;
import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.games.model.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class CartDetailDTOTests {

    @BeforeEach
    public void setup() {

    }

    @Test
    void should_return_CartDetailToCartDetailDTO_Object() {
        //given
        final Game game = Game.builder()
                .id(1)
                .title("TestTitle")
                .type("TestType")
                .price(100)
                .description("TestDescription")
                .rating(10)
                .availability(true).build();

        final GameDTO gameDTO = GameDTO.from(game);

        final CartDetail cartDetail = new CartDetail(game,0);
        //when
        final CartDetailDTO remakeObject = CartDetailDTO.from(cartDetail);
        //then
        final CartDetailDTO expectedObject = new CartDetailDTO(gameDTO,0);
        Assertions.assertEquals(remakeObject,expectedObject);


    }

    @Test
    void fromList() {
    }
}