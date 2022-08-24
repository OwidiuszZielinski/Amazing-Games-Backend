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
    private String accesslevel;


    public static UserDTO from (UserEntity user){
        return UserDTO.builder().build();
    }
}
