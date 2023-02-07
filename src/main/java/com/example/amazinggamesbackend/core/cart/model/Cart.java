package com.example.amazinggamesbackend.core.cart.model;

import com.example.amazinggamesbackend.core.users.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {


    @GeneratedValue(strategy = GenerationType.TABLE)
    @Id
    private int id;

    @OneToMany(mappedBy = "cart",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CartDetail> cartDetails = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")

    private User user;


}
