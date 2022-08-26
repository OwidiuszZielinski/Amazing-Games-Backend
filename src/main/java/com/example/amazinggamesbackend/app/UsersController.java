package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.core.users.UsersService;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UsersController {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    private UsersService usersService;
    @Operation(summary = "add user")
    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody UserDTO userDTO) {

        if (!usersRepository.findByUsername(userDTO.getUsername()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else if (userDTO.getUsername().isBlank() || userDTO.getPassword().isBlank() || userDTO.getEmail().isBlank()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            usersService.addUser(userDTO);
            return ResponseEntity.ok().build();
        }
    }
    @Operation(summary = "get users")
    @GetMapping("/users")
    public List<UserEntity> getUsers(){
        return usersService.getAllUsers();
    }

    @Operation(summary = "delete user by id")
    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id){
        usersService.deleteUser(id);
    }

    @Operation(summary = "edit user by id")
    @PatchMapping("/users/{id}")
    public UserEntity editUser(@PathVariable int id,@RequestBody UserDTO user){
        return usersService.editUser(id,user);
    }
}
