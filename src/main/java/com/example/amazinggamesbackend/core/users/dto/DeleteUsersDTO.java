package com.example.amazinggamesbackend.core.users.dto;

import com.example.amazinggamesbackend.core.users.model.UserEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteUsersDTO {
    private List<Integer> Ids;

    public static DeleteUsersDTO from(UserEntity userEntity){
        return DeleteUsersDTO.builder().build();
    }

}
