package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.games.GamesService;
import com.example.amazinggamesbackend.core.games.dto.GamesDTO;
import com.example.amazinggamesbackend.core.users.dto.UsersDTO;
import com.example.amazinggamesbackend.core.users.model.UserService;
import com.example.amazinggamesbackend.core.users.model.UsersEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Operation(summary = "add user")
    @PostMapping("/users")
    public String addUser(@RequestBody UsersDTO usersDTO){
        if(usersDTO == null){
            return "user can't be null";
        }
        else if(usersDTO.getUsername().isBlank() || usersDTO.getPassword().isBlank() || usersDTO.getEmail().isBlank()){
            return "username or password or email can't be blank";
        }
        else
        userService.addUser(usersDTO);
        return "user successfully added";
    }

    @Operation(summary = "get users")
    @GetMapping("/users")
    public List<UsersEntity> getUsers(){
        return userService.getAllUsers();
    }

    @Operation(summary = "delete user by id")
    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id){
        userService.deleteUser(id);
    }

}
