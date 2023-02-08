package com.example.amazinggamesbackend.core.orders;

import com.example.amazinggamesbackend.core.games.GameService;
import com.example.amazinggamesbackend.core.games.model.Game;
import com.example.amazinggamesbackend.core.orders.dto.CreateOrderDTO;
import com.example.amazinggamesbackend.core.orders.dto.EditOrderDTO;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import com.example.amazinggamesbackend.core.orders.model.Order;
import com.example.amazinggamesbackend.core.orders.model.OrderStatus;
import com.example.amazinggamesbackend.core.tax.Rates;
import com.example.amazinggamesbackend.config.Tax;
import com.example.amazinggamesbackend.core.users.UserService;
import com.example.amazinggamesbackend.core.users.model.User;
import com.example.amazinggamesbackend.interfaces.FormatValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@RequiredArgsConstructor
@Service
public class OrderService implements FormatValue {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final GameService gameService;

    public CreateOrderDTO createOrder(CreateOrderDTO order) {
        Order newOrder = Order.builder().status(OrderStatus.CREATED)
                .date(Order.orderDate())
                .games(addGamesToOrder(order.getGames()))
                .value(gameService.calculateOrderValue(order.getGames()))
                .user(userService.userById(order.getUser()))
                .build();

        orderRepository.save(newOrder);
        return order;
    }

    public Set<Game> addGamesToOrder(List<Integer> gameIds) {
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
        for (Order order : getOrders()) {
            orderList.add(OrderDTO.from(order));
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

    public List<OrderDTO> setTax(List<OrderDTO> orderList) {
        for (OrderDTO order : orderList) {
            order.setValueWithTax(calculateTax(order.getValue(), userService.userById(order.getUser())));
        }
        return orderList;
    }

    public void deleteOrders(List<Integer> orderIds) {
        if (orderIds.isEmpty()) {
            throw new IllegalArgumentException("Ids is empty");
        }
        if (getAllById(orderIds).isEmpty()) {
            throw new IllegalArgumentException("Order not found in DB");
        }
        orderRepository.deleteAllByIdInBatch(orderIds);

    }

    public List<Order> getAllById(List<Integer> orderIds) {
        return orderRepository.findAllById(orderIds);
    }


    public EditOrderDTO updateOrder(int orderId, EditOrderDTO order) {
        Order orderById = getOrderById(orderId);
        orderById.setStatus(order.getStatus());
        orderById.setGames(gameService.gamesInOrder(order.getGames()));
        orderById.setValue(format(gameService.calculateOrderValue(order.getGames())));
        orderRepository.save(orderById);
        return order;
    }


    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException("This order not found"));
    }


    public double calculateTax(double priceWithoutTax, User user) {
        double tax = 0;
        for (Rates rate : Tax.readTaxFromFile()) {
            if (rate.getCountry_id() == user.getCountry_id()) {
                tax = priceWithoutTax * (rate.getStandard_rate() / 100);
            }
        }
        return format(priceWithoutTax + tax);
    }

    @Override
    public double format(double value) {
        final int places = 2;
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.FLOOR);
        return bigDecimal.doubleValue();
    }
}

