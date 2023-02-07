package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.games.exceptions.FreeGame;
import com.example.amazinggamesbackend.core.games.model.GameDayDiscount;
import com.example.amazinggamesbackend.core.games.model.Game;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DiscountService {

    private final GameDayDiscountRepository gameDayDiscountRepository;
    private final GameService gameService;


    //TODO: val!
    public GameDayDiscount discountGame() {
        if (emptyDiscounts()) {
            GameDayDiscount gameDayDiscount = new GameDayDiscount();
            gameDayDiscount.setGame(GameDTO.to(randomGame()));
            gameDayDiscountRepository.save(gameDayDiscount);
            return gameDayDiscount;
        }
        GameDayDiscount existsDiscount = getDiscount();
        existsDiscount.setGame(GameDTO.to(randomGame()));
        gameDayDiscountRepository.save(existsDiscount);
        return existsDiscount;
    }

    public boolean emptyDiscounts() {
        return gameDayDiscountRepository.findAll().size() == 0;
    }

    private GameDayDiscount getDiscount() {
        return gameDayDiscountRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No discount in DB"));
    }


    private GameDTO randomGame() {
        return GameDTO.from(gameService.getGameById(randomDiscountGameId()));
    }

    public int randomDiscountGameId() {
        int discount = new Random().nextInt(0, paidGames().size() - 1);
        return (paidGames().size() == 1) ? firstGameDiscount() : paidGames().get(discount).getId();
    }


    private int firstGameDiscount() {
        return paidGames().stream().findFirst().orElseThrow(FreeGame::new).getId();
    }

    public List<GameDTO> paidGames() {
        return getAllGames().stream()
                .filter(game -> game.getPrice() > 0)
                .collect(Collectors.toList());
    }

    public List<GameDTO> getAllGames() {
        return gameService.getGames();
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
