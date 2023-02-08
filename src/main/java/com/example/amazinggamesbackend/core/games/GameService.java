package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.games.exceptions.GameNotFound;
import com.example.amazinggamesbackend.core.games.model.Game;
import com.example.amazinggamesbackend.core.orders.OrderService;
import com.example.amazinggamesbackend.core.orders.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;


    public GameDTO addGame(GameDTO gameDTO) {
        if (checkTitle(gameDTO)) {
            throw new IllegalArgumentException("Game exists in DB");
        }
        if (gameDTO.getTitle().isBlank() || gameDTO.getType().isBlank() || gameDTO.getDescription().isBlank()) {
            throw new IllegalArgumentException("Blank field");
        }
        gameDTO.setRating(checkAndSetRating(gameDTO));
        Game newGame = new Game();
        newGame.fromDTO(gameDTO);
        gameRepository.save(newGame);
        return gameDTO;
    }

    public GameDTO updateGame(int gameId, GameDTO gameDTO) {
        checkAndSetRating(gameDTO);
        Game game = getGameById(gameId);
        game.fromDTO(gameDTO);
        gameRepository.save(game);
        return gameDTO;
    }

    public void deleteGamesById(List<Integer> gameIds) {
        if (gameIds.isEmpty()) {
            throw new IllegalArgumentException("gameIds to delete is empty");
        }
        if (getAllByIds(gameIds).isEmpty()) {
            throw new IllegalArgumentException("Games not found in DB");
        }
        otherEntityCleaner(gameIds);
        gameRepository.deleteAllByIdInBatch(gameIds);

    }

    public boolean checkTitle(GameDTO game) {
        return getGames().stream()
                .anyMatch(g -> g.getTitle()
                        .equals(game.getTitle()));
    }

    public double checkAndSetRating(GameDTO game) {
        return game.getRating() < 0 || game.getRating() > 10 ? 0 : game.getRating();
    }

    public List<GameDTO> getGames() {
        return getAllGames().stream()
                .map(GameDTO::from)
                .collect(Collectors.toList());
    }



    public void otherEntityCleaner(List<Integer> gameIds) {
        clearGamesInCart(gameIds);
        clearGamesInOrder(gameIds);
    }

    public void clearGamesInCart(List<Integer> gameIds) {

        for (Game x : getAllByIds(gameIds)) {
            x.getCartDetails().remove(x.getCartDetails());
            gameRepository.save(x);
        }

    }

    public void clearGamesInOrder(List<Integer> gameIds) {
        for (Game x : getAllByIds(gameIds)) {
            x.getCartDetails().remove(x.getCartDetails());
            gameRepository.save(x);
        }

    }



    public double calculateOrderValue(List<Integer> games) {
        return getAllByIds(games).stream()
                .mapToDouble(Game::getPrice)
                .sum();
    }

    public List<Game> getAllByIds(List<Integer> gameIds) {
        return gameRepository.findAllById(gameIds);
    }

    public Set<Game> gamesInOrder(List<Integer> gameIds) {
        return new HashSet<>(getAllByIds(gameIds));
    }

    public Game getGameById(int gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new GameNotFound("Not found"));
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public boolean checkGameExists(List<Integer> gameIds) {
        return getAllByIds(gameIds)
                .stream()
                .anyMatch(game -> game.getId()
                        == gameIds.stream()
                        .findAny()
                        .orElseThrow(() -> new NoSuchElementException("No game in reposiotry")));
    }


    //@Scheduled(fixedRate = 100000)
    public List<Game> freeGames(List<Game> games) {
        return games.stream()
                .filter(game -> game.getPrice() == 0)
                .collect(Collectors.toList());
    }


}
