package com.example.amazinggamesbackend.core.orders;

import com.example.amazinggamesbackend.core.games.GamesRepository;
import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.games.model.GamesEntity;
import com.example.amazinggamesbackend.core.orders.dto.OrdersDTO;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.example.amazinggamesbackend.core.users.UserService;
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
    UserService userService = new UserService();
    @Autowired
    GamesService gamesService = new GamesService();




    public OrderEntity addorder(OrdersDTO order) {
        OrderEntity neworder = new OrderEntity();
        neworder.addUser(userService.userById(order.getUser()));
        neworder.setGamesEntities(gamesService.gamesPerOrder(order));
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
    public OrderEntity editOrder(int id,OrdersDTO orderEntity){
        OrderEntity getOrder = orderRepository.findById(id).get();
        getOrder.setDate(orderEntity.getDate());
        getOrder.setStatus(orderEntity.getStatus());
        getOrder.setUser(userService.userById(orderEntity.getUser()));
        getOrder.setGamesEntities(gamesService.gamesPerOrder(orderEntity));
        getOrder.setValue(gamesService.calculateOrderValue(orderEntity));
        return orderRepository.save(getOrder);

    }

}
