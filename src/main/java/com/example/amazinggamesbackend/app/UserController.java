package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.core.users.dto.UsersDTO;
import com.example.amazinggamesbackend.core.users.UserService;
import com.example.amazinggamesbackend.core.users.model.UsersEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    private UserService userService;
    @Operation(summary = "add user")
    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody UsersDTO usersDTO) {

        if (!usersRepository.findByUsername(usersDTO.getUsername()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else if (usersDTO.getUsername().isBlank() || usersDTO.getPassword().isBlank() || usersDTO.getEmail().isBlank()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            userService.addUser(usersDTO);
            return ResponseEntity.ok().build();
        }
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
