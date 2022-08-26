package com.example.amazinggamesbackend.core.orders.model;

import com.example.amazinggamesbackend.core.games.model.GameEntity;

import com.example.amazinggamesbackend.core.users.model.UserEntity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
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
    @JsonManagedReference
    private List<GameEntity> gamesEntities = new ArrayList<>();

    public static String orderdate() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        return ts.toString();

    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setValue(double value) {
        DecimalFormat formatvalue = new DecimalFormat("##.00");
        this.value = Double.parseDouble(formatvalue.format(value).replace("," ,"."));
    }


    public void addUser(UserEntity userEntity) {
        this.user = userEntity;
    }


}
