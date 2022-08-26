package com.example.amazinggamesbackend.core.users.model;

import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEntity {

        @Id
        @GeneratedValue
        private Integer id;

        private String username;
        private String password;
        private String email;
        private String accesslevel;
        @OneToMany(fetch = FetchType.LAZY, mappedBy = "user",cascade = CascadeType.MERGE)
        @JsonBackReference
        private List<OrderEntity> orderEntities;
        public void fromDTO(UserDTO userDTO){
        this.username = userDTO.getUsername();
        this.email = userDTO.getEmail();
        this.accesslevel = userDTO.getAccesslevel();
        this.password = userDTO.getPassword();

    }


}
