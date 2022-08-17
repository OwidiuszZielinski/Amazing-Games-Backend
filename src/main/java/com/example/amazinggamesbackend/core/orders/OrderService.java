package com.example.amazinggamesbackend.core.orders;

import com.example.amazinggamesbackend.core.games.GamesRepository;
import com.example.amazinggamesbackend.core.orders.dto.OrdersDTO;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.core.users.model.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    GamesRepository gamesRepository;

    public OrderEntity addorder(OrdersDTO order){
        double value = 0;
        OrderEntity neworder = new OrderEntity();
        neworder.addUser(usersRepository.findById(order.getUser()).get());
        for(Integer x : order.getGames()){
            neworder.addGame(gamesRepository.findById(x).get());
            value += gamesRepository.findById(x).get().getPrice();
        }
        neworder.setValue(value);
        neworder.setStatus(order.getStatus());
        neworder.setDate(OrderEntity.orderdate());
        return orderRepository.save(neworder);

    }
    public ArrayList<OrderEntity> getAllOrders(){
        return (ArrayList<OrderEntity>) orderRepository.findAll();
    }

}