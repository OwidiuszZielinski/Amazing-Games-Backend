package com.example.amazinggamesbackend.core.orders;

import com.example.amazinggamesbackend.core.games.GameService;
import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.games.model.Game;
import com.example.amazinggamesbackend.core.orders.dto.CreateOrderDTO;
import com.example.amazinggamesbackend.core.orders.dto.EditOrderDTO;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import com.example.amazinggamesbackend.core.orders.model.Order;
import com.example.amazinggamesbackend.core.orders.model.OrderStatus;
import com.example.amazinggamesbackend.core.tax.Rates;
import com.example.amazinggamesbackend.core.tax.Tax;
import com.example.amazinggamesbackend.core.users.UserService;
import com.example.amazinggamesbackend.core.users.model.User;
import com.example.amazinggamesbackend.interfaces.FormatValue;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.text.DecimalFormat;
import java.util.*;

@RequiredArgsConstructor
@Service
public class OrderService implements FormatValue {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final GameService gameService;

    public void createOrder(CreateOrderDTO order) {
        Order newOrder = Order.builder().status(OrderStatus.CREATED)
                .date(Order.orderDate())
                .games(addGamesToOrder(order.getGames()))
                .value(gameService.calculateOrderValue(order.getGames()))
                .user(userService.userById(order.getUser()))
                .build();
        orderRepository.save(newOrder);
    }

    private Set<Game> addGamesToOrder(List<Integer> gameIds) {
        if (gameIds.isEmpty()) {
            throw new IllegalArgumentException("Empty games list");
        }
        if (gameService.checkGameExists(gameIds)) {
            return gameService.gamesInOrder(gameIds);
        } else
            throw new IllegalArgumentException("This game not exists at repository");

    }

    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orderList = new ArrayList<>();
        for (Order x : getOrders()) {
            orderList.add(OrderDTO.from(x));
        }
        setTax(orderList);
        orderList
                .sort(Comparator.comparing(OrderDTO::getDate)
                        .reversed());
        return orderList;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public void setTax(List<OrderDTO> orderList) {
        for (OrderDTO y : orderList) {
            y.setValueWithTax(calcTax(y.getValue() ,userService.userById(y.getUser())));
        }
    }

    public void deleteOrders(List<Integer> ids) {
        if (ids.isEmpty()) {
            throw new IllegalArgumentException("Ids is empty");
        }
        if (getAllById(ids).isEmpty()) {
            throw new IllegalArgumentException("Order not found in DB");
        }
        orderRepository.deleteAllByIdInBatch(ids);

    }

    private List<Order> getAllById(List<Integer> ids) {
        return orderRepository.findAllById(ids);
    }


    public void updateOrder(int id ,EditOrderDTO order) {
        Order orderById = getOrderById(id);
        orderById.setStatus(order.getStatus());
        orderById.setGames(gameService.gamesInOrder(order.getGames()));
        orderById.setValue(format(gameService.calculateOrderValue(order.getGames())));
        orderRepository.save(orderById);

    }


    private Order getOrderById(int id) {
        return orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("This order not found"));
    }


    public double calcTax(double withoutTax ,User user) {
        double tax = 0;
        for (Rates x : Tax.getInstance().getRates()) {
            if (x.getCountry_id() == user.getCountry_id()) {
                tax = withoutTax * (x.getStandard_rate() / 100);
            }
        }
        return format(withoutTax + tax);
    }

    @Override
    public double format(double value) {
        DecimalFormat formatValue = new DecimalFormat("##.00");
        return Double.parseDouble(formatValue.format(value).replace("," ,"."));
    }
}

