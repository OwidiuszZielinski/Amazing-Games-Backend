package com.example.amazinggamesbackend.core.cart.model;

import com.example.amazinggamesbackend.core.users.model.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
public class CartEntity {

    @Id
    @GeneratedValue
    private int id;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CartDetail> cartDetails = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")

    private UserEntity user;


    public void addUser(UserEntity userEntity) {
        this.user = userEntity;
    }






}
