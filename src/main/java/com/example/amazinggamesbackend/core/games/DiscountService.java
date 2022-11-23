package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.exceptions.NoPaidGame;
import com.example.amazinggamesbackend.core.games.model.GameDayDiscount;
import com.example.amazinggamesbackend.core.games.model.GameEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    private final GameDayDiscountRepository gameDayDiscountRepository;
    private final GameRepository gameRepository;

    public DiscountService(GameDayDiscountRepository gameDayDiscountRepository ,GameRepository gameRepository) {
        this.gameDayDiscountRepository = gameDayDiscountRepository;
        this.gameRepository = gameRepository;
    }

    public void discountGame(){
        GameDayDiscount gameDayDiscount = new GameDayDiscount();
        if (!noDiscount()) {
            gameDayDiscount = getDiscount();
        }
        gameDayDiscount.setGameEntity(randomGame());
        gameDayDiscountRepository.save(gameDayDiscount);
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
    public List<GameEntity> getAllGames() {
        return gameRepository.findAll();
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
