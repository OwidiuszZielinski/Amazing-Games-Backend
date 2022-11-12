package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.dto.GameEntityDTO;

import com.example.amazinggamesbackend.core.games.model.GameDayDiscount;
import com.example.amazinggamesbackend.core.games.model.GameEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GamesService {

    @Autowired
    GamesRepository gamesRepository;

    @Autowired
    GameDayDiscountRepository gameDayDiscountRepository;

    public void addGame(GameEntityDTO game) {
        GameEntity newGame = new GameEntity();
        newGame.fromDTO(game);
        gamesRepository.save(newGame);
    }

    public void discountGame(){
        GameDayDiscount gameDayDiscount = new GameDayDiscount();
        if(gameDayDiscountRepository.findAll().size() ==0){
            gameDayDiscount.setGameEntity(gamesRepository.findById(randomDiscountGameId()).get());
            gameDayDiscountRepository.save(gameDayDiscount);
        }
        else
            gameDayDiscount = gameDayDiscountRepository.findAll().get(0);
            gameDayDiscount.setGameEntity(gamesRepository.findById(randomDiscountGameId()).get());
            gameDayDiscountRepository.save(gameDayDiscount);
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

        gamesRepository.deleteAllByIdInBatch(ids);

    }

    public void clearCartDetailsInGameEntity(List<Integer> ids){

        for(GameEntity x : gamesRepository.findAllById(ids)){
            x.getCartDetails().clear();
            gamesRepository.save(x);
        }

    }


    public void updateGame(int id ,GameEntityDTO gameDTO) {
        GameEntity game = getGameById(id);
        game.fromDTO(gameDTO);
        gamesRepository.save(game);
    }

    public double calculateOrderValue(List<Integer> games) {
        return gamesRepository.findAllById(games).stream().mapToDouble(GameEntity::getPrice).sum();
    }

    //Service method return Entity
    public Set<GameEntity> gamesInOrder(List<Integer> games) {
        return new HashSet<>(gamesRepository.findAllById(games));
    }

    //Service method return Entity
    public GameEntity getGameById(int id) {
        return gamesRepository.findById(id).get();
    }

    public List<GameEntity> getAllGames() {
        return gamesRepository.findAll();
    }

    //Nie wiem czy to optymalne zapisywac do pliku przecene codzinnie o 1 w nocy i odczytywac z pliku pobierajac
    //Byc moze lepiej uzyc cache
    //@Cacheable(cacheNames = "discountGame")
    //@Scheduled(fixedRate = 100000)
    //@Scheduled(fixedRate = 10000)
    public int randomDiscountGameId() {
        var gameList = paidGames(getAllGames());
        var random = new Random();
        int discount = random.nextInt(0 ,gameList.size() - 1);
        return gameList.get(discount).getId();

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
