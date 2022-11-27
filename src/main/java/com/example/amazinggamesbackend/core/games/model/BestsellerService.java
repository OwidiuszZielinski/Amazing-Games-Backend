package com.example.amazinggamesbackend.core.games.model;

import com.example.amazinggamesbackend.core.games.GameService;
import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.orders.OrderService;
import com.example.amazinggamesbackend.core.orders.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BestsellerService {

    private final OrderService orderService;
    private final GameService gameService;


    public GameDTO bestseller() {
        if (orderService.getAllOrders().isEmpty()) {
            throw new NoSuchElementException("No orders");
        }
        HashMap<Integer, Integer> gameIdFrequency = new HashMap<>();
        suchBestseller(gameIdFrequency);
        int key = Collections.max(gameIdFrequency.entrySet() ,Map.Entry.comparingByValue()).getKey();
        return GameDTO.from(gameService.getGameById(key));

    }


    private Map<Integer,Integer> suchBestseller(HashMap<Integer, Integer> gameIdFrequency) {
        for (Order x : orderService.getOrders()) {
            for (Game y : x.getGames()) {
                if (x.getGames().isEmpty()) {
                    throw new NoSuchElementException("No orders");
                }
                if (gameIdFrequency.containsKey(y.getId())) {
                    gameIdFrequency.put(y.getId() ,gameIdFrequency.get(y.getId()) + 1);
                } else {
                    gameIdFrequency.put(y.getId() ,1);
                }
            }
        }
        return gameIdFrequency;
    }
}
