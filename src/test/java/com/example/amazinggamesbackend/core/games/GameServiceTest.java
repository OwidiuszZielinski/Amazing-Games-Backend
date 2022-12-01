package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.cart.CartRepository;
import com.example.amazinggamesbackend.core.cart.CartService;
import com.example.amazinggamesbackend.core.cart.exceptions.CartNotFound;
import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.games.model.Game;
import com.example.amazinggamesbackend.core.users.UserRepository;
import com.example.amazinggamesbackend.core.users.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {


    private GameRepository gameRepository;
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        gameRepository = mock(GameRepository.class);
        gameService = new GameService(gameRepository);
    }



    @Test
    void should_addGame_params_OK() {
        //given
        final Game game = Game.builder().id(1)
                .title("test")
                .type("test")
                .description("example").build();
        GameDTO gameDT0 = GameDTO.from(game);
        //then
        gameService.addGame(gameDT0);
        //when
        verify(gameRepository).save(Mockito.any(Game.class));


    }
    @Test
    void should_addGame_empty_title() {
        //given
        final Game game = Game.builder().id(1)
                .title("")
                .type("test")
                .description("example").build();
        GameDTO gameDT0 = GameDTO.from(game);
        //then
        //when
        Assertions.assertThrows(IllegalArgumentException.class,()-> gameService.addGame(gameDT0));

    }
    @Test
    void should_addGame_title_exists() {
        //given
        final List<Game> givenGames = new ArrayList<>();
        givenGames.add(Game.builder().id(1).title("test").build());
        when(gameRepository.findAll()).thenReturn(givenGames);
        final Game game = Game.builder().id(2)
                .title("test")
                .type("test")
                .description("example").build();
        GameDTO gameDT0 = GameDTO.from(game);
        //then
        //when
        Assertions.assertThrows(IllegalArgumentException.class,()-> gameService.addGame(gameDT0));

    }

    @Test
    void checkTitle() {
    }

    @Test
    void checkAndSetRating() {
    }

    @Test
    void getGames() {
    }

    @Test
    void deleteGamesById() {
    }

    @Test
    void clearGamesInCart() {
    }

    @Test
    void clearGamesInOrder() {
    }

    @Test
    void updateGame() {
    }

    @Test
    void calculateOrderValue() {
    }

    @Test
    void gamesInOrder() {
    }

    @Test
    void getGameById() {
    }

    @Test
    void getAllGames() {
    }

    @Test
    void checkGameExists() {
    }

    @Test
    void freeGames() {
    }
}