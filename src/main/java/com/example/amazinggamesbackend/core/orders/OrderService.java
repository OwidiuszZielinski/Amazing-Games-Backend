package com.example.amazinggamesbackend.core.orders;

import com.example.amazinggamesbackend.core.games.GamesRepository;
import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.games.model.GamesEntity;
import com.example.amazinggamesbackend.core.orders.dto.OrdersDTO;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.core.users.model.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    GamesRepository gamesRepository;


    GamesService gamesService = new GamesService();
    //dodac 1 repo na serwis
    public OrderEntity addorder(OrdersDTO order) {
        OrderEntity neworder = new OrderEntity();
        neworder.addUser(usersRepository.findById(order.getUser()).get());
        neworder.setGamesEntities(gamesRepository.findAllById(order.getGames()).stream().collect(Collectors.toList()));
        neworder.setValue(gamesService.calculateOrderValue(order));
        neworder.setStatus(neworder.getStatus());
        neworder.setDate(OrderEntity.orderdate());
        return orderRepository.save(neworder);
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }
    public void deleteOrder(int id){
        orderRepository.deleteById(id);
    }
    public OrderEntity editOrder(int id,OrdersDTO order){
        OrderEntity getOrder = orderRepository.findById(id).get();
        getOrder.setDate(order.getDate());
        getOrder.setStatus(order.getStatus());
        getOrder.setUser(usersRepository.findById(order.getUser()).get());
        getOrder.setGamesEntities(gamesRepository.findAllById(order.getGames()).stream().collect(Collectors.toList()));
        getOrder.setValue(gamesService.calculateOrderValue(order));
        return orderRepository.save(getOrder);

    }

}
