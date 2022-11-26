package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.exceptions.NoPaidGame;
import com.example.amazinggamesbackend.core.games.model.GameDayDiscount;
import com.example.amazinggamesbackend.core.games.model.GameEntity;
import lombok.RequiredArgsConstructor;
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
    //Strworzylem beana w configuracji zeby nie tworzyc nowych obiektow
    private GameDayDiscount discount;

    public DiscountService(GameDayDiscountRepository gameDayDiscountRepository ,GameRepository gameRepository ,GameDayDiscount discount) {
        this.gameDayDiscountRepository = gameDayDiscountRepository;
        this.gameRepository = gameRepository;
        this.discount = discount;
    }

    public void discountGame() {
        if (isDiscount()) {
            discount = getDiscount();
        }
        int discountId = getDiscountId(discount);
        while (discountId == discount.getGame().getId()) {
            discount.setGame(randomGame());
        }
        gameDayDiscountRepository.save(discount);
    }

    private int getDiscountId(GameDayDiscount gameDayDiscount) {
        return gameDayDiscount.getGame().getId();
    }


    private GameDayDiscount getDiscount() {
        return gameDayDiscountRepository.findAll().stream().findFirst().orElseThrow(() -> new RuntimeException("No discount in DB"));
    }


    private GameEntity randomGame() {
        GameEntity discountGame = gameRepository.findById(randomDiscountGameId()).orElse(null);

        return discountGame;
    }

    private boolean isDiscount() {
        return gameDayDiscountRepository.findAll().size() != 0;
    }

    public int randomDiscountGameId() {
        int discount = new Random().nextInt(0 ,paidGames().size() - 1);
        return (paidGames().size() == 1) ? firstGameDiscount() : paidGames().get(discount).getId();
    }



    private int firstGameDiscount() {
        return paidGames().stream().findFirst().orElseThrow(NoPaidGame::new).getId();
    }

    public boolean checkPayGames() {
        return getAllGames().stream().anyMatch(game -> game.getPrice() != 0);
    }




    public List<GameEntity> paidGames() {
        return getAllGames().stream()
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
