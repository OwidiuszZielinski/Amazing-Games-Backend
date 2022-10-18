package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.dto.GameEntityDTO;
import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.orders.dto.CreateOrderDTO;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class GamesService {

    @Autowired
    GamesRepository gamesRepository;

    public void addGame(GameEntityDTO game) {
        GameEntity newGame = new GameEntity();
        newGame.fromDTO(game);
        gamesRepository.save(newGame);
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
    public List<GameEntity> gamesInOrder(List<Integer> games) {
        return new ArrayList<>(gamesRepository.findAllById(games));
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
    @Scheduled(cron = "0 0 1 * * *")
    //@Scheduled(fixedRate = 10000)
    public void randomDiscountGame() {
        var gameList = paidGames(getAllGames());
        var random = new Random();
        int discount = random.nextInt(0 ,gameList.size() - 1);
        saveDiscountGameToFile(gameList.get(discount).getId());

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
