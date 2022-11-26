package com.example.amazinggamesbackend.core.orders.model;

import com.example.amazinggamesbackend.core.games.model.Game;
import com.example.amazinggamesbackend.core.users.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
//Builder musi posiadac konstruktory ?? UpdateOrder bez setterow jak ?
//@Data == @Getter, @Setter, @EqualsAndHashcode, @ToString
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;
    private String date;
    private double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "order_game",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private Set<Game> games = new HashSet<>();

    public static String orderDate() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        return ts.toString();

    }



}
