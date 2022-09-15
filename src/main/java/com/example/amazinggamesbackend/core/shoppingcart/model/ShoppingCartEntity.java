package com.example.amazinggamesbackend.core.shoppingcart.model;

import com.example.amazinggamesbackend.core.shoppingcart.ShoppingCartGames;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "cart_user")
public class ShoppingCartEntity {
    @Id
    @GeneratedValue
    private int id;

    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.ALL})
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<ShoppingCartGames> cartGames = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public void addUser(UserEntity userEntity) {
        this.user = userEntity;
    }



}
