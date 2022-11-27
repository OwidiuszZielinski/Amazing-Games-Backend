package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.games.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameService {
    private final GameRepository gameRepository;


    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;

    }

    public void addGame(GameDTO game){
        if(checkTitle(game)){
            throw new IllegalArgumentException("Game exists in DB");
        }
        if(game.getTitle().isBlank() || game.getType().isBlank() || game.getDescription().isBlank()){
            throw new IllegalArgumentException("Blank field");
        }
        game.setRating(checkAndSetRating(game));
        Game newGame = new Game();
        newGame.fromDTO(game);
        gameRepository.save(newGame);
    }

    public boolean checkTitle(GameDTO game){
        return getGames().stream()
                .anyMatch(g -> g.getTitle()
                        .equals(game.getTitle()));
    }

    public double checkAndSetRating(GameDTO game){
        return game.getRating()<0||game.getRating()>10 ? 0 : game.getRating();
    }

    public List<GameDTO> getGames() {
//        List<GameEntityDTO> tempGames = new ArrayList<>();
//        for (GameEntity x : getAllGames()) {
//            tempGames.add(GameEntityDTO.from(x));
//        }
        return getAllGames().stream()
                .map(GameDTO::from)
                .collect(Collectors.toList());
    }

    public void deleteGamesById(List<Integer> ids){
        if(ids.isEmpty()){
            throw new IllegalArgumentException("ids to delete is empty");
        }
        if (getAllByIds(ids).isEmpty()) {
            throw new IllegalArgumentException("Games not found in DB");
        }
        otherEntityCleaner(ids);
        gameRepository.deleteAllByIdInBatch(ids);

    }

    private void otherEntityCleaner(List<Integer> ids) {
        clearGamesInCart(ids);
        clearGamesInOrder(ids);
    }

    public void clearGamesInCart(List<Integer> ids) {
        for (Game x : getAllByIds(ids)) {
            x.getCartDetails().clear();
            gameRepository.save(x);
        }

    }
    public void clearGamesInOrder(List<Integer> ids) {
        for (Game x : getAllByIds(ids)) {
            x.getOrders().clear();
            gameRepository.save(x);
        }

    }
    public void updateGame(int id ,GameDTO gameDTO) {
        if(checkTitle(gameDTO)){
            throw new IllegalArgumentException("This title exists");
        }
        checkAndSetRating(gameDTO);
        Game game = getGameById(id);
        game.fromDTO(gameDTO);
        gameRepository.save(game);
    }

    public double calculateOrderValue(List<Integer> games) {
        return getAllByIds(games).stream()
                .mapToDouble(Game::getPrice)
                .sum();
    }

    private List<Game> getAllByIds(List<Integer> games) {
        return gameRepository.findAllById(games);
    }

    //Service method return Entity
    public Set<Game> gamesInOrder(List<Integer> games) {
        return new HashSet<>(getAllByIds(games));
    }

    //Service method return Entity
    public Game getGameById(int id) {
        return gameRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Not found"));
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public boolean checkGameExists(List<Integer> gameIds){
        return getAllByIds(gameIds)
                .stream()
                .anyMatch(game -> game.getId()
                        .equals(gameIds.stream()
                                .findAny()
                                .orElseThrow(()->new NoSuchElementException("No game in reposiotry"))));
    }

    //Nie wiem czy to optymalne zapisywac do pliku przecene codzinnie o 1 w nocy i odczytywac z pliku pobierajac
    //Byc moze lepiej uzyc cache
    //@Cacheable(cacheNames = "discountGame")
    //@Scheduled(fixedRate = 100000)
    //@Scheduled(fixedRate = 10000)

    public static List<Game> freeGames(List<Game> list) {
        return list.stream()
                .filter(game -> game.getPrice() == 0)
                .collect(Collectors.toList());
    }




}
