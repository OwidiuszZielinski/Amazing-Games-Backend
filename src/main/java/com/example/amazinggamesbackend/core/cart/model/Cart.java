package com.example.amazinggamesbackend.core.cart.model;

import com.example.amazinggamesbackend.core.users.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
public class Cart {

    @Id
    @GeneratedValue
    private int id;

    @OneToMany(mappedBy = "cart",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CartDetail> cartDetails = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")

    private User user;


    public void addUser(User user) {
        this.user = user;
    }






}
