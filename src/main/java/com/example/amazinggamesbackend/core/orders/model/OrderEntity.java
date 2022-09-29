package com.example.amazinggamesbackend.core.orders.model;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.orders.dto.OrderDTO;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import com.example.amazinggamesbackend.interfaces.FormatValue;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//Builder musi posiadac konstruktory ??
//@Data == @Getter, @Setter, @EqualsAndHashcode, @ToString
public class OrderEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;
    private String date;
    private double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST ,CascadeType.MERGE ,CascadeType.DETACH ,CascadeType.REFRESH })
    @JoinTable(name = "order_game",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private List<GameEntity> games = new ArrayList<>();

    public static String orderDate() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        return ts.toString();

    }

//    public void setValue(double value) {
//        this.value = format(value);
//    }

}
