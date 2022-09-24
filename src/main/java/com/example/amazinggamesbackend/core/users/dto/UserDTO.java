package com.example.amazinggamesbackend.core.users.dto;

import com.example.amazinggamesbackend.core.users.model.UserEntity;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {


    private String username;
    private String password;
    private String email;
    private String roles;
    private String address;
    private int country;


    public static UserDTO from(UserEntity user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .roles(user.getRoles())
                .address(user.getAddress())
                .country(user.getCountry())
                .build();
    }
}
