package com.example.amazinggamesbackend.core.orders;

import com.example.amazinggamesbackend.core.games.GameService;
import com.example.amazinggamesbackend.core.games.model.Game;
import com.example.amazinggamesbackend.core.orders.dto.CreateOrderDTO;
import com.example.amazinggamesbackend.core.orders.dto.EditOrderDTO;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import com.example.amazinggamesbackend.core.orders.model.Order;
import com.example.amazinggamesbackend.core.orders.model.OrderStatus;
import com.example.amazinggamesbackend.core.users.UserService;
import com.example.amazinggamesbackend.core.users.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private  UserService userService;
    private  GameService gameService;


    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        gameService = mock(GameService.class);
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository,userService,gameService);

    }

    @Test
    void should_create_new_order_with_params_OK() {
        //given
        final Game givenGame = Game.builder().id(1).build();
        final Order givenOrder = Order.builder().id(1).user(new User()).games(Set.of(givenGame)).build();
        final CreateOrderDTO orderFromDTO = CreateOrderDTO.from(givenOrder);
        //when
        when(gameService.checkGameExists(orderFromDTO.getGames())).thenReturn(true);
        orderService.createOrder(orderFromDTO);
        //then
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderArgumentCaptor.capture());
    }

    @Test
    void should_get_all_orders_from_repository_return_ListDTO() {
        //when
        List<OrderDTO> orders = orderService.getAllOrders();
        //then
        verify(orderRepository).findAll();
        assertNotNull(orders);
    }

    @Test
    void should_get_all_orders_from_repository_return_ListModel() {
        //when
        List<Order> orders = orderService.getOrders();
        //then
        verify(orderRepository).findAll();
        assertNotNull(orders);

    }

    @Test
    void should_calculate_tax_from_ratesList_params_OK() {
        //given
        final double orderValue = 5.00;
        final User user = new User();
        user.setCountry_id(7);
        final OrderDTO orderDTO = OrderDTO.builder().value(orderValue).user(1).build();
        final List<OrderDTO> givenOrderList = Collections.singletonList(orderDTO);
        when(userService.userById(1)).thenReturn(user);
        //when
        List<OrderDTO> orderDTOS = orderService.setTax(givenOrderList);
        //then
        final double expectedTax = 6.15;
        assertNotNull(orderDTOS);
        final double valueWithTax = orderDTOS.stream().findFirst().get().getValueWithTax();
        assertEquals(expectedTax,valueWithTax);
    }

    @Test
    void should_delete_orders_by_ids() {
        //given
        final List<Integer> toDeleteList= Collections.singletonList(1);
        Order order = Order.builder().id(1).build();
        when(orderRepository.findAllById(toDeleteList)).thenReturn(Collections.singletonList(order));
        //when
        orderService.deleteOrders(toDeleteList);
        //then
        verify(orderRepository).deleteAllByIdInBatch(toDeleteList);


    }

    @Test
    void should_update_order_by_id_params_OK() {
        //given
        final int givenID = 1;
        final OrderStatus givenStatus = OrderStatus.STARTED;
        final Order beforeUpdateOrder = Order.builder().id(givenID).status(givenStatus).build();

        when(orderRepository.findById(givenID)).thenReturn(Optional.of(beforeUpdateOrder));
        //when
        final OrderStatus expectedStatus = OrderStatus.CREATED;
        final Order afterUpdateOrder = Order.builder().id(givenID).status(expectedStatus).build();
        final EditOrderDTO expectedOrder = EditOrderDTO.builder().status(expectedStatus).build();

        when(orderRepository.save(Mockito.any(Order.class))).thenReturn(afterUpdateOrder);
        final EditOrderDTO afterUpdateOrderDTO = orderService.updateOrder(givenID ,expectedOrder);

        //then
        verify(orderRepository).save(beforeUpdateOrder);
        assertEquals(expectedStatus,afterUpdateOrderDTO.getStatus());

    }

    @Test
    void should_calculate_tax_by_user_country() {
        //given
        final double orderValue = 5.00;
        final User user = new User();
        user.setCountry_id(7);
        when(userService.userById(Mockito.anyInt())).thenReturn(user);
        //when
        final double calculate = orderService.calculateTax(orderValue,user);
        //then
        final double expectedTax = 6.15;
        assertEquals(expectedTax,calculate);



    }

    @Test
    void should_format_order_value() {
        //given
        final double givenValue = 1.999999999;
        //when
        final double afterFormat = orderService.format(givenValue);
        //then
        final double expectedValue = 1.99;
        assertEquals(expectedValue,afterFormat);

    }
    @Test
    void should_get_order_by_id(){
        //given
        final int givenId = 1;
        final Order expectedOrder = Order.builder().id(givenId).build();
        when(orderRepository.findById(anyInt())).thenReturn(Optional.ofNullable(expectedOrder));
        //then
        final Order order = orderService.getOrderById(givenId);
        //when
        verify(orderRepository).findById(givenId);
        assertNotNull(order);
        assertEquals(expectedOrder,order);

    }
    @Test
    void should_get_orders_by_ids(){
        //given
        final List<Integer> givenIds = Arrays.asList(1,2);
        final Order orderOne = Order.builder().id(givenIds.get(0)).build();
        final Order orderTwo = Order.builder().id(givenIds.get(1)).build();
        final List<Order> givenOrders = Arrays.asList(orderOne,orderTwo);
        when(orderRepository.findAllById(anyCollection())).thenReturn(givenOrders);
        //then
        final List<Order> ordersFrom = orderService.getAllById(givenIds);
        //when
        final int expectedSize = 2;
        verify(orderRepository).findAllById(givenIds);
        assertNotNull(ordersFrom);
        assertEquals(expectedSize,ordersFrom.size());

    }

}