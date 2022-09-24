package com.example.amazinggamesbackend.core.orders;

import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.games.dto.GameEntityDTO;
import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.example.amazinggamesbackend.core.users.UsersService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.CollationElementIterator;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Service
public class OrdersService {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    UsersService usersService = new UsersService();
    @Autowired
    GamesService gamesService = new GamesService();


    public void createOrder(OrderDTO order) {
        OrderEntity newOrder = new OrderEntity(order.getStatus() ,OrderEntity.orderDate() ,gamesService.calculateOrderValue(order) ,
                usersService.userById(order.getUser()) ,gamesService.gamesInOrder(order));
        ordersRepository.save(newOrder);
    }

    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orderList = new ArrayList<>();
        for (OrderEntity x : ordersRepository.findAll()) {
            orderList.add(OrderDTO.from(x));
        }
        return orderList;
    }

    public void deleteOrders(List<Integer> ids) {
        ordersRepository.deleteAllByIdInBatch(ids);
    }

    public OrderDTO editOrder(int id ,OrderDTO order) {
        OrderEntity getOrder = ordersRepository.findById(id).get();
        getOrder.setStatus(order.getStatus());
        getOrder.setUser(usersService.userById(order.getUser()));
        getOrder.setGamesEntities(gamesService.gamesInOrder(order));
        getOrder.setValue(gamesService.calculateOrderValue(order));
        ordersRepository.save(getOrder);
        return OrderDTO.from(getOrder);

    }

    public GameEntityDTO bestseller() {
        HashMap<Integer, Integer> gameIdFrequency = new HashMap<>();
        for (OrderEntity x : ordersRepository.findAll()) {
            for (GameEntity y : x.getGamesEntities()) {
                if (gameIdFrequency.containsKey(y.getId())) {
                    gameIdFrequency.put(y.getId() ,gameIdFrequency.get(y.getId()) + 1);
                } else {
                    gameIdFrequency.put(y.getId() ,1);
                }
            }
        }
        int key = Collections.max(gameIdFrequency.entrySet(),Map.Entry.comparingByValue()).getKey();
        return GameEntityDTO.from(gamesService.getGameById(key));


    }
}

