package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.cart.CartRepository;
import com.example.amazinggamesbackend.core.cart.CartService;
import com.example.amazinggamesbackend.core.cart.exceptions.CartNotFound;
import com.example.amazinggamesbackend.core.cart.model.CartDetail;
import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.games.model.Game;
import com.example.amazinggamesbackend.core.users.UserRepository;
import com.example.amazinggamesbackend.core.users.UserService;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.stream.Collectors;

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
        GameDTO gameAdded = gameService.addGame(gameDT0);
        //when
        verify(gameRepository).save(Mockito.any(Game.class));
        assertEquals(gameAdded.getTitle() ,game.getTitle());


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
    void should_check_title_exists() {
        //given
        final List<Game> givenGames = new ArrayList<>();
        givenGames.add(Game.builder().id(1).title("test").build());
        final Game firstGame = givenGames.stream().findFirst().get();
        when(gameRepository.findAll()).thenReturn(givenGames);
        //then
        boolean checkTitle = gameService.checkTitle(GameDTO.from(firstGame));
        //when
        verify(gameRepository).findAll();
        assertTrue(checkTitle);
    }
    @Test
    void should_check_title_doesnt_exists() {
        //given
        final List<Game> givenGames = new ArrayList<>();
        givenGames.add(Game.builder().id(1).title("test").build());
        final Game firstGame = givenGames.stream().findFirst().get();
        when(gameRepository.findAll()).thenReturn(givenGames);
        //then
        givenGames.clear();
        boolean checkTitle = gameService.checkTitle(GameDTO.from(firstGame));
        //when
        verify(gameRepository).findAll();
        assertFalse(checkTitle);
    }

    @Test
    void should_check_and_set_rating_toBigRating() {
        //given
        double givenRating = 11;
        GameDTO givenGame = GameDTO.builder().rating(givenRating).build();
        double expectedRating = 0;
        //when
        double rating = gameService.checkAndSetRating(givenGame);
        givenGame.setRating(rating);
        //then
        Assertions.assertEquals(expectedRating,givenGame.getRating());
    }
    @Test
    void should_check_and_set_rating_NegativeRating() {
        //given
        double givenRating = -1;
        GameDTO givenGame = GameDTO.builder().rating(givenRating).build();
        double expectedRating = 0;
        //when
        double rating = gameService.checkAndSetRating(givenGame);
        givenGame.setRating(rating);
        //then
        Assertions.assertEquals(expectedRating,givenGame.getRating());
    }
    @Test
    void should_check_and_set_rating_OK_Rating() {
        //given
        double givenRating = 5;
        GameDTO givenGame = GameDTO.builder().rating(givenRating).build();
        double expectedRating = 5;
        //when
        double rating = gameService.checkAndSetRating(givenGame);
        givenGame.setRating(rating);
        //then
        Assertions.assertEquals(expectedRating,givenGame.getRating());
    }


    @Test
    void should_return_gamesList_one_game_in_repository() {
        //given
        final Game game = Game.builder().id(1)
                .title("test")
                .type("test")
                .description("example").build();
        final List<Game> games = List.of(game);
        when(gameRepository.findAll()).thenReturn(games);
        //when
        final List<GameDTO> dtoList = gameService.getGames();
        verify(gameRepository).findAll();
        Assertions.assertEquals(games.size(),dtoList.size());


    }

    @Test
    void should_delete_game_from_repository() {
        //given
        final List<Integer> ids = Collections.singletonList(1);
        final List<Game> games =
                Collections.singletonList(
                        Game.builder()
                        .id(1)
                        .cartDetails(Collections.singletonList(new CartDetail(null,1)))
                        .build()
                );
        when(gameRepository.findAllById(ids)).thenReturn(games);
        //when
        gameService.deleteGamesById(ids);
        //then
        verify(gameRepository).deleteAllByIdInBatch(ids);
        verify(gameRepository,times(2)).save(Mockito.any(Game.class));
    }

    @Test
    void should_clear_game_in_cart_details() {
        //given
        final List<Integer> givenIds = Collections.singletonList(1);
        final List<Game> givenList =
                Collections.singletonList(
                        Game.builder()
                                .id(1)
                                .cartDetails(Collections.singletonList(new CartDetail(null,1)))
                                .build()
                );

        when(gameRepository.findAllById(givenIds)).thenReturn(givenList);
        //when
        gameService.clearGamesInCart(givenIds);
        //then
        verify(gameRepository,times(1)).save(Mockito.any(Game.class));

    }

    @Test
    void should_clear_game_in_order() {
        //given
        final List<Integer> givenIds = Collections.singletonList(1);
        final List<Game> givenList =
                Collections.singletonList(
                        Game.builder()
                                .id(1)
                                .cartDetails(Collections.singletonList(new CartDetail(null,1)))
                                .build()
                );

        when(gameRepository.findAllById(givenIds)).thenReturn(givenList);
        //when
        gameService.clearGamesInOrder(givenIds);
        //then
        verify(gameRepository,times(1)).save(Mockito.any(Game.class));
    }

    @Test
    void should_update_game_params_OK() {
        //given
        final Game beforeUpdate = Game.builder().id(1)
                .title("test")
                .type("test")
                .description("example").build();
        final Game expected = Game.builder().id(1)
                .title("updated")
                .type("updated")
                .description("updated").build();
        when(gameRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(beforeUpdate));
        when(gameRepository.save(Mockito.any(Game.class))).thenReturn(expected);
        //when
        final GameDTO afterUpdate = gameService.updateGame(beforeUpdate.getId() ,GameDTO.from(expected));
        //then
        verify(gameRepository,times(1)).findById(beforeUpdate.getId());
        verify(gameRepository,times(1)).save(Mockito.any(Game.class));
        assertEquals(afterUpdate.getTitle() ,expected.getTitle());

    }

    @Test
    void should_calculate_order_value_two_games_in_order_params_OK() {
        //given
        final double expectedPrice = 8.9;
        final List<Integer> givenIds = Collections.singletonList(1);
        final List<Game> givenList =
                List.of(
                        Game.builder()
                                .id(1)
                                .price(4.5)
                                .build(),
                        Game.builder()
                                .id(2)
                                .price(4.4)
                                .build()
                );

        when(gameRepository.findAllById(anyCollection())).thenReturn(givenList);
        //when
        double calculated = gameService.calculateOrderValue(givenIds);
        //then
        verify(gameRepository,times(1)).findAllById(givenIds);
        Assertions.assertEquals(expectedPrice,calculated);

    }

    @Test
    void should_return_games_inOrder_with_ids_from_list() {
        //given
        final int expectedId = 1;
        final List<Integer> givenIds = Collections.singletonList(expectedId);
        final List<Game> givenList =
                List.of(
                        Game.builder()
                                .id(1)
                                .price(4.5)
                                .build(),
                        Game.builder()
                                .id(2)
                                .price(4.4)
                                .build()
                );
        when(gameRepository.findAllById(givenIds)).thenReturn(givenList);

        //when
        final Set<Game> gamesFrom = gameService.gamesInOrder(givenIds);
        boolean anyMatch = gamesFrom.stream().anyMatch(game -> game.getId() == expectedId);
        //then
        verify(gameRepository).findAllById(givenIds);
        Assertions.assertTrue(anyMatch);

    }
    @Test
    void should_return_games_inOrder_with_ids_from_list_not_exists() {
        //given
        final int expectedId = 3;
        final List<Integer> givenIds = Collections.singletonList(expectedId);
        final List<Game> givenList =
                List.of(
                        Game.builder()
                                .id(1)
                                .price(4.5)
                                .build(),
                        Game.builder()
                                .id(2)
                                .price(4.4)
                                .build()
                );
        when(gameRepository.findAllById(givenIds)).thenReturn(givenList);

        //when
        final Set<Game> gamesFrom = gameService.gamesInOrder(givenIds);
        boolean anyMatch = gamesFrom.stream().anyMatch(game -> game.getId() == expectedId);
        //then
        verify(gameRepository).findAllById(givenIds);
        Assertions.assertFalse(anyMatch);

    }

    @Test
    void should_return_game_by_Id_params_OK() {
        //given
        final int givenId = 1;
        final String givenTitle = "givenTitle";
        final Game givenGame = Game.builder()
                                .id(givenId)
                                .title(givenTitle)
                                .price(4.5)
                                .build();

        when(gameRepository.findById(anyInt())).thenReturn(Optional.of(givenGame));
        //when
        final Game gameById = gameService.getGameById(givenId);
        final String expectedTitle = gameById.getTitle();
        //then
        verify(gameRepository).findById(givenId);
        Assertions.assertNotNull(gameById);
        Assertions.assertEquals(givenTitle,expectedTitle);
    }

    @Test
    void should_return_games_list() {
        //given
        final List<Game> givenList =
                List.of(
                        Game.builder()
                                .id(1)
                                .price(4.5)
                                .build(),
                        Game.builder()
                                .id(2)
                                .price(4.4)
                                .build()
                );
        final int expectedListSize = 2;
        when(gameRepository.findAll()).thenReturn(givenList);
        //when
        final List<Game> allGames = gameService.getAllGames();
        final int allGamesSize = allGames.size();
        //then
        verify(gameRepository).findAll();
        assertEquals(expectedListSize,allGamesSize);

    }

    @Test
    void check_game_exists_at_repository_params_OK() {
        //given
        final int expectedId = 1;
        final List<Integer> givenIds = Collections.singletonList(expectedId);
        final List<Game> givenList =
                List.of(
                        Game.builder()
                                .id(1)
                                .price(4.5)
                                .build(),
                        Game.builder()
                                .id(2)
                                .price(4.4)
                                .build()
                );
        when(gameRepository.findAllById(givenIds)).thenReturn(givenList);
        //when
        final boolean checkGame = gameService.checkGameExists(givenIds);
        //then
        verify(gameRepository).findAllById(givenIds);
        assertTrue(checkGame);

    }
    @Test
    void check_game_exists_at_repository_params_BAD() {
        //given
        final int givenId = 3;
        final List<Integer> givenIds = Collections.singletonList(givenId);
        final List<Game> givenList =
                List.of(
                        Game.builder()
                                .id(1)
                                .price(4.5)
                                .build(),
                        Game.builder()
                                .id(2)
                                .price(4.4)
                                .build()
                );
        when(gameRepository.findAllById(givenIds)).thenReturn(givenList);
        //when
        final boolean checkGame = gameService.checkGameExists(givenIds);
        //then
        verify(gameRepository).findAllById(givenIds);
        assertFalse(checkGame);

    }

    @Test
    void should_return_free_games_in_repository_params_OK() {
        //given
        final int givenFreeGame = 1;
        final List<Game> givenList =
                List.of(
                        Game.builder()
                                .id(1)
                                .price(4.5)
                                .build(),

                        Game.builder()
                                .id(2)
                                .price(0)
                                .build()
                );
        when(gameRepository.findAll()).thenReturn(givenList);
        //when
        final List<Game> allGames = gameService.getAllGames();
        final List<Game> freeGames = gameService.freeGames(allGames);
        final int expectedSize = freeGames.size();
        //then
        verify(gameRepository,times(1)).findAll();
        assertEquals(expectedSize,givenFreeGame);
    }
    @Test
    void should_return_free_games_in_repository_No_Free_Games() {
        //given
        final int givenFreeGame = 0;
        final List<Game> givenList =
                List.of(
                        Game.builder()
                                .id(1)
                                .price(4.5)
                                .build(),

                        Game.builder()
                                .id(2)
                                .price(4.2)
                                .build()
                );
        final int givenSize = givenList.size();
        when(gameRepository.findAll()).thenReturn(givenList);
        //when
        final List<Game> allGames = gameService.getAllGames();
        final List<Game> freeGames = gameService.freeGames(allGames);
        final int expectedSize = freeGames.size();
        //then
        verify(gameRepository,times(1)).findAll();
        assertEquals(expectedSize,givenFreeGame);
    }
}