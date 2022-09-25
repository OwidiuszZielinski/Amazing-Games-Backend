package com.example.amazinggamesbackend.core.users.dto;

import com.example.amazinggamesbackend.core.users.model.UserEntity;
import lombok.*;

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
    private int country_id;


    public static UserDTO from(UserEntity user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .roles(user.getRoles())
                .address(user.getAddress())
                .country_id(user.getCountry_id())
                .build();
    }
    public static UserDTO fromWithoutPassword(UserEntity user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .address(user.getAddress())
                .country_id(user.getCountry_id())
                .build();
    }
}
