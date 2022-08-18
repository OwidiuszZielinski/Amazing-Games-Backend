package com.example.amazinggamesbackend.core.users.model;

import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.example.amazinggamesbackend.core.users.dto.UsersDTO;
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
public class UsersEntity {

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
        public void fromDTO(UsersDTO usersDTO){
        this.username = usersDTO.getUsername();
        this.email = usersDTO.getEmail();
        this.accesslevel = usersDTO.getAccesslevel();
        this.password = usersDTO.getPassword();

    }


}
