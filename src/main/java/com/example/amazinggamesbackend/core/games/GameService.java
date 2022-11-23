package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.dto.GameEntityDTO;
import com.example.amazinggamesbackend.core.games.exceptions.NoPaidGame;
import com.example.amazinggamesbackend.core.games.model.GameDayDiscount;
import com.example.amazinggamesbackend.core.games.model.GameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final GameDayDiscountRepository gameDayDiscountRepository;
    @Autowired
    public GameService(GameRepository gameRepository ,GameDayDiscountRepository gameDayDiscountRepository) {
        this.gameRepository = gameRepository;
        this.gameDayDiscountRepository = gameDayDiscountRepository;
    }

    public void addGame(GameEntityDTO game) {
        GameEntity newGame = new GameEntity();
        newGame.fromDTO(game);
        gameRepository.save(newGame);
    }

    public void discountGame(){
        GameDayDiscount gameDayDiscount = new GameDayDiscount();
        if(noDiscount()){
            gameDayDiscount.setGameEntity(randomGame());
            gameDayDiscountRepository.save(gameDayDiscount);
        }
        else {
            gameDayDiscount = getDiscount();
            gameDayDiscount.setGameEntity(randomGame());
            gameDayDiscountRepository.save(gameDayDiscount);
        }
    }


    private GameDayDiscount getDiscount() {
        return gameDayDiscountRepository.findAll().stream().findFirst().orElseThrow(()->new RuntimeException("No discount in DB"));
    }


    private GameEntity randomGame() {
        return gameRepository.findById(randomDiscountGameId()).orElse(null);
    }

    private boolean noDiscount() {
        return gameDayDiscountRepository.findAll().size() == 0;
    }


    public List<GameEntityDTO> getGames() {
        List<GameEntityDTO> tempGames = new ArrayList<>();
        for (GameEntity x : getAllGames()) {
            tempGames.add(GameEntityDTO.from(x));
        }
        return tempGames;

    }
    public void deleteGamesById(List<Integer> ids) {
        clearCartDetailsInGameEntity(ids);

        gameRepository.deleteAllByIdInBatch(ids);

    }

    public void clearCartDetailsInGameEntity(List<Integer> ids){

        for(GameEntity x : gameRepository.findAllById(ids)){
            x.getCartDetails().clear();
            gameRepository.save(x);
        }

    }


    public void updateGame(int id ,GameEntityDTO gameDTO) {
        GameEntity game = getGameById(id);
        game.fromDTO(gameDTO);
        gameRepository.save(game);
    }

    public double calculateOrderValue(List<Integer> games) {
        return gameRepository.findAllById(games).stream().mapToDouble(GameEntity::getPrice).sum();
    }

    //Service method return Entity
    public Set<GameEntity> gamesInOrder(List<Integer> games) {
        return new HashSet<>(gameRepository.findAllById(games));
    }

    //Service method return Entity
    public GameEntity getGameById(int id) {
        return gameRepository.findById(id).get();
    }

    public List<GameEntity> getAllGames() {
        return gameRepository.findAll();
    }

    //Nie wiem czy to optymalne zapisywac do pliku przecene codzinnie o 1 w nocy i odczytywac z pliku pobierajac
    //Byc moze lepiej uzyc cache
    //@Cacheable(cacheNames = "discountGame")
    //@Scheduled(fixedRate = 100000)
    //@Scheduled(fixedRate = 10000)
    public int randomDiscountGameId() {
        List<GameEntity> gameList = paidGames(getAllGames());
        Random random = new Random();
        if(gameList.size()==1){
            return firstGameDiscount(gameList);
        }
        if (checkPayGames(gameList)) {
            int discount = random.nextInt(0 ,gameList.size() - 1);
            return gameList.get(discount).getId();

        }
        else
            throw new NoPaidGame();


    }

    private Integer firstGameDiscount(List<GameEntity> gameList) {
        return gameList.stream().findFirst().orElseThrow(NoPaidGame::new).getId();
    }

    public boolean checkPayGames(List<GameEntity> games){
        return games.stream().anyMatch(game -> game.getPrice()!=0);
    }


    public static List<GameEntity> paidGames(List<GameEntity> list) {
        return list.stream()
                .filter(game -> game.getPrice() > 0)
                .collect(Collectors.toList());
    }
    public static List<GameEntity> freeGames(List<GameEntity> list) {
        return list.stream()
                .filter(game -> game.getPrice() == 0)
                .collect(Collectors.toList());
    }



    public void saveDiscountGameToFile(int id) {
        try {
            File file = new File("src/main/resources/discount.txt");
            FileWriter fw = new FileWriter(file);
            fw.write(String.valueOf(id));
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int getDiscountGameFromFile() throws FileNotFoundException {
        File file = new File("src/main/resources/discount.txt");
        Scanner scanner = new Scanner(new File(String.valueOf(file)));
        return Integer.parseInt(scanner.next());

    }

}
