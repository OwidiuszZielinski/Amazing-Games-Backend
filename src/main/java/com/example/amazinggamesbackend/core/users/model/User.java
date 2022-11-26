package com.example.amazinggamesbackend.core.users.model;

import com.example.amazinggamesbackend.core.baseclasses.BaseEntity;
import com.example.amazinggamesbackend.core.cart.model.Cart;
import com.example.amazinggamesbackend.core.orders.model.Order;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@JsonIgnoreProperties({ "hibernateLazyInitializer" ,"handler" })
public class User extends BaseEntity implements UserDetails {
    private String username;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;
    private int country_id;
    private String address;

    private String roles;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.MERGE)
    @JsonBackReference
    private List<Order> orders;



    @OneToOne(mappedBy = "user", cascade = CascadeType.MERGE)
    @JsonBackReference
    Cart cart;



    public void fromDTO(UserDTO userDTO) {

        this.username = userDTO.getUsername();
        this.roles = userDTO.getRoles();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.country_id = userDTO.getCountry_id();
        this.address = userDTO.getAddress();

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
