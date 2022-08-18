package com.example.amazinggamesbackend.core.orders.model;

import com.example.amazinggamesbackend.core.games.dto.GamesDTO;
import com.example.amazinggamesbackend.core.games.model.GamesEntity;
import com.example.amazinggamesbackend.core.orders.OrderRepository;
import com.example.amazinggamesbackend.core.orders.dto.OrdersDTO;
import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.core.users.model.UsersEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@Entity
public class OrderEntity {




    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String status;
    private String date;
    private double value;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="user_id")
    private UsersEntity user;


    @ManyToMany(fetch=FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(name = "order_game",
            joinColumns = @JoinColumn (name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    @JsonManagedReference
    private List<GamesEntity> gamesEntities = new ArrayList<>();

    public static String orderdate(){
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        return ts.toString();

    }
    public void addGame(GamesEntity game){
        gamesEntities.add(game);
        System.out.println(gamesEntities);
    }


    public void addUser(UsersEntity userEntity) {
        this.user = userEntity;
    }



}
