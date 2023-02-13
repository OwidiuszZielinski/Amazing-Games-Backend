package com.example.amazinggamesbackend.core.users.dto;

import com.example.amazinggamesbackend.core.users.model.Role;
import com.example.amazinggamesbackend.core.users.model.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private int id;
    @NotEmpty(message = "The name is required.")
    @Size(min = 6, max = 30, message = "The length of name must be between 6 and 30 characters.")
    private String username;
    private String password;
    @Email
    @NotEmpty(message = "The EMAIL is required.")
    @Size(min = 6, max = 30, message = "The length of EMAIL must be between 6 and 30 characters.")
    private String email;
    private Role role;
    private String address;
    private int country_id;


    public static UserDTO from(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .address(user.getAddress())
                .country_id(user.getCountry_id())
                .build();
    }
    public static UserDTO fromWithoutPassword(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .address(user.getAddress())
                .country_id(user.getCountry_id())
                .build();
    }
}
