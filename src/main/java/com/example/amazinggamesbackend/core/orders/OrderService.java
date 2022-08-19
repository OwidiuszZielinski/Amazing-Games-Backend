package com.example.amazinggamesbackend.core.orders;

import com.example.amazinggamesbackend.core.games.GamesRepository;
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

    public OrderEntity addorder(OrdersDTO order) {
        OrderEntity neworder = new OrderEntity();
        neworder.addUser(usersRepository.findById(order.getUser()).get());
        neworder.setGamesEntities(gamesRepository.findAllById(order.getGames()).stream().collect(Collectors.toList()));
        neworder.setValue(gamesRepository.findAllById(order.getGames()).stream().mapToDouble(GamesEntity::getPrice).sum());
        neworder.setStatus(order.getStatus());
        neworder.setDate(OrderEntity.orderdate());
        return orderRepository.save(neworder);
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }
    public void deleteOrder(int id){
        orderRepository.deleteById(id);
    }
    public OrderEntity editOrder(int id,OrdersDTO orderEntity){
        OrderEntity getOrder = orderRepository.findById(id).get();
        getOrder.setDate(orderEntity.getDate());
        getOrder.setStatus(orderEntity.getStatus());
        getOrder.setUser(usersRepository.findById(orderEntity.getUser()).get());
        getOrder.setGamesEntities(gamesRepository.findAllById(orderEntity.getGames()).stream().collect(Collectors.toList()));
        getOrder.setValue(gamesRepository.findAllById(orderEntity.getGames()).stream().mapToDouble(GamesEntity::getPrice).sum());
        return orderRepository.save(getOrder);

    }

}
