package com.example.amazinggamesbackend.core.orders;

import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.example.amazinggamesbackend.core.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersService {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    UsersService usersService = new UsersService();
    @Autowired
    GamesService gamesService = new GamesService();


    public void createOrder(OrderDTO order) {
        OrderEntity newOrder = new OrderEntity(order.getStatus(),OrderEntity.orderDate(),gamesService.calculateOrderValue(order),
                usersService.userById(order.getUser()),gamesService.gamesInOrder(order));
                ordersRepository.save(newOrder);
    }

    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orderList = new ArrayList<>();
        for(OrderEntity x : ordersRepository.findAll()){
            orderList.add(OrderDTO.from(x));
        }
        return orderList;
    }

    public void deleteOrders(List<Integer>ids) {
        ordersRepository.deleteAllByIdInBatch(ids);
    }
    public OrderDTO editOrder(int id,OrderDTO order){
        OrderEntity getOrder = ordersRepository.findById(id).get();
        getOrder.setStatus(order.getStatus());
        getOrder.setUser(usersService.userById(order.getUser()));
        getOrder.setGamesEntities(gamesService.gamesInOrder(order));
        getOrder.setValue(gamesService.calculateOrderValue(order));
        ordersRepository.save(getOrder);
        return OrderDTO.from(getOrder);

    }

}
