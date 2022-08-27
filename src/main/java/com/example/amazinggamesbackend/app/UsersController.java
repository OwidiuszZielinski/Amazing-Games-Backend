package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.core.users.UsersService;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@RequiredArgsConstructor
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UsersController {



    @Autowired
    UsersRepository usersRepository;
    @Autowired
    private UsersService usersService;
    @Operation(summary = "add user")
    @PostMapping
    public ResponseEntity register(@Valid @RequestBody UserDTO user) {
            usersService.addUser(user);
            return ResponseEntity.ok().build();


    }

    @Operation(summary = "get users")
    @GetMapping
    public List<UserEntity> getUsers(){
        return usersService.getAllUsers();
    }

    @Operation(summary = "delete user by id")
    @DeleteMapping
    public void deleteUserById(@PathVariable Long id){
        usersService.deleteUser(id);
    }

    @Operation(summary = "edit user by id")
    @PatchMapping
    public UserEntity editUser(@PathVariable Long id,@RequestBody UserDTO user){
        return usersService.editUser(id,user);
    }
}


