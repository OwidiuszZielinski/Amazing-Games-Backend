package com.example.amazinggamesbackend.core.orders;

import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.example.amazinggamesbackend.core.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    UsersService usersService = new UsersService();
    @Autowired
    GamesService gamesService = new GamesService();


    public OrderEntity addorder(OrderDTO order) {
        OrderEntity neworder = new OrderEntity();
        neworder.addUser(usersService.userById(order.getUser()));
        neworder.setGamesEntities(gamesService.gamesPerOrder(order));
        neworder.setValue(gamesService.calculateOrderValue(order));
        neworder.setStatus(neworder.getStatus());
        neworder.setDate(OrderEntity.orderdate());
        return ordersRepository.save(neworder);
    }

    public List<OrderEntity> getAllOrders() {
        return ordersRepository.findAll();
    }

    public void deleteOrders(List<Integer>ids) {
        ordersRepository.deleteAllByIdInBatch(ids);
    }
    public OrderEntity editOrder(int id,OrderDTO order){
        OrderEntity getOrder = ordersRepository.findById(id).get();
        getOrder.setDate(order.getDate());
        getOrder.setStatus(order.getStatus());
        getOrder.setUser(usersService.userById(order.getUser()));
        getOrder.setGamesEntities(gamesService.gamesPerOrder(order));
        getOrder.setValue(gamesService.calculateOrderValue(order));
        return ordersRepository.save(getOrder);

    }

}
