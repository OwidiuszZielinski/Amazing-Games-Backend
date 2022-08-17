package com.example.amazinggamesbackend.core.orders.model;

import com.example.amazinggamesbackend.core.games.dto.GamesDTO;
import com.example.amazinggamesbackend.core.games.model.GamesEntity;
import com.example.amazinggamesbackend.core.orders.dto.OrdersDTO;
import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.core.users.model.UsersEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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


    @ManyToOne
    @JoinColumn (name="user_id")
    private UsersEntity user;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<GamesEntity> gamesEntities = new ArrayList<>();

    public static String orderdate(){
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Date date=new Date(ts.getTime());
        return date.toString();

    }
    public void addGame(GamesEntity game){
        gamesEntities.add(game);
        game.setOrder(this);
    }
    public void setStatus(String status) {
        this.status = "STARTED";
    }
    public void addUser(UsersEntity userEntity) {
        this.user = userEntity;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }


}
