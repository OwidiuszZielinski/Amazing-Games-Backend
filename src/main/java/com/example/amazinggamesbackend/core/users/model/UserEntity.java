package com.example.amazinggamesbackend.core.users.model;

import com.example.amazinggamesbackend.core.baseclasses.BaseEntity;
import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer" ,"handler" })
public class UserEntity extends BaseEntity implements UserDetails {
    private String username;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;


    private String roles;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.MERGE)
    @JsonBackReference
    private List<OrderEntity> orderEntities;



    @OneToOne(mappedBy = "user", cascade = CascadeType.MERGE)
    @JsonBackReference
    ShoppingCartEntity shoppingCart;

    public UserEntity(String username ,String password ,String email ,String roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public void fromDTO(UserDTO userDTO) {
        this.username = userDTO.getUsername();
        this.roles = userDTO.getRoles();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((new SimpleGrantedAuthority(roles)));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}