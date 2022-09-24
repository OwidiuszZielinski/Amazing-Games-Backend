package com.example.amazinggamesbackend.core.orders.model;

import com.example.amazinggamesbackend.core.cart.model.CartDetail;
import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.games.model.GameEntity;

import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.core.users.UsersService;
import com.example.amazinggamesbackend.core.users.model.UserEntity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class OrderEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int status;
    private String date;
    private double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST ,CascadeType.MERGE ,CascadeType.DETACH ,CascadeType.REFRESH })
    @JoinTable(name = "order_game",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private List<GameEntity> gamesEntities = new ArrayList<>();

    public static String orderDate() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        return ts.toString();

    }

    public OrderEntity(int status ,String date ,double value ,UserEntity user ,List<GameEntity> gamesEntities) {
        this.status = status;
        this.date = date;
        this.value = value;
        this.user = user;
        this.gamesEntities = gamesEntities;
    }

    public void setValue(double value) {
        DecimalFormat formatValue = new DecimalFormat("##.00");
        this.value = Double.parseDouble(formatValue.format(value).replace("," ,"."));
    }



}
