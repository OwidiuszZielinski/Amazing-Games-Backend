package com.example.amazinggamesbackend.core.orders;

import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.games.dto.GameEntityDTO;
import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.orders.dto.CreateOrderDTO;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.example.amazinggamesbackend.core.orders.model.OrderStatus;
import com.example.amazinggamesbackend.core.tax.Rates;
import com.example.amazinggamesbackend.core.tax.Tax;
import com.example.amazinggamesbackend.core.users.UsersService;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import com.example.amazinggamesbackend.interfaces.FormatValue;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrdersService implements FormatValue {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    UsersService usersService;
    @Autowired
    GamesService gamesService;


    public void createOrder(CreateOrderDTO order) {
        OrderEntity newOrder = OrderEntity.builder().status(OrderStatus.STARTED)
                .date(OrderEntity.orderDate())
                .games(gamesService.gamesInOrder(order.getGames()))
                .value(gamesService.calculateOrderValue(order.getGames()))
                .user(usersService.userById(order.getUser()))
                .build();
                ordersRepository.save(newOrder);
    }

    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orderList = new ArrayList<>();
        for (OrderEntity x : ordersRepository.findAll()) {
            orderList.add(OrderDTO.from(x));
        }
        setTax(orderList);
        orderList.sort(Comparator.comparing(OrderDTO::getDate).reversed());
        return orderList;
    }

    public void setTax(List<OrderDTO> orderList){
        for (OrderDTO y : orderList) {
            y.setValueWithTax(calcTax(y.getValue() ,usersService.userById(y.getUser())));
        }
    }

    public void deleteOrders(List<Integer> ids) {
        ordersRepository.deleteAllByIdInBatch(ids);

    }


    public void updateOrder(int id ,CreateOrderDTO order) {
        OrderEntity getOrder = ordersRepository.findById(id).get();
        getOrder.setStatus(order.getStatus());
        if(order.getUser()<=0) {
            getOrder.setUser(getOrder.getUser());
        }else {
            getOrder.setUser(usersService.userById(order.getUser()));
        }
            getOrder.setGames(gamesService.gamesInOrder(order.getGames()));
            getOrder.setValue(format(gamesService.calculateOrderValue(order.getGames())));
            ordersRepository.save(getOrder);

    }

    public GameEntityDTO bestseller() {
        HashMap<Integer, Integer> gameIdFrequency = new HashMap<>();
        for (OrderEntity x : ordersRepository.findAll()) {
            for (GameEntity y : x.getGames()) {
                if (gameIdFrequency.containsKey(y.getId())) {
                    gameIdFrequency.put(y.getId() ,gameIdFrequency.get(y.getId()) + 1);
                } else {
                    gameIdFrequency.put(y.getId() ,1);
                }
            }
        }
        int key = Collections.max(gameIdFrequency.entrySet() ,Map.Entry.comparingByValue()).getKey();
        return GameEntityDTO.from(gamesService.getGameById(key));

    }

    public double calcTax(double withoutTax ,UserEntity user) {
        double tax = 0;
        for (Rates x : Tax.getInstance().getRates()) {
            if (x.getCountry_id() == user.getCountry_id()) {
                tax = withoutTax * (x.getStandard_rate() / 100);
            }
        }
        return format(withoutTax + tax);
    }
}

