package com.example.amazinggamesbackend.core.users;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsersRepository usersRepository;


    public List<UserEntity> getAllUsers(){
        return usersRepository.findAll();
    }



    public void deleteUsers(List<Integer> ids){
        usersRepository.deleteAllByIdInBatch(ids);
    }
    public UserEntity editUser(int id,UserDTO user){
        UserEntity editedUser = usersRepository.findById(id).get();
        editedUser.setEmail(user.getEmail());
        editedUser.setRoles(user.getRoles());
        editedUser.setUsername(user.getUsername());
        if(user.getPassword().isBlank()) {
            editedUser.setPassword(editedUser.getPassword());
        }
        else {
            editedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return usersRepository.save(editedUser);
    }
    public UserEntity userById(int id){
        return usersRepository.findById(id).get();
    }

}
