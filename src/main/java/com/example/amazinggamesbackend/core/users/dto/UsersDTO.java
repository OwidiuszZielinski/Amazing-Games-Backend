package com.example.amazinggamesbackend.core.users.dto;

import com.example.amazinggamesbackend.core.users.model.UsersEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDTO {

    private String username;
    private String password;
    private String email;
    private String accesslevel;


    public static UsersDTO from (UsersEntity user){
        return UsersDTO.builder().build();
    }
}
