package com.example.amazinggamesbackend.core.users;

import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public UserEntity addUser(UserDTO user){

        UserEntity newuser = new UserEntity();
        newuser.fromDTO(user);
        return usersRepository.save(newuser);
    }
    public List<UserEntity> getAllUsers(){
        return usersRepository.findAll();
    }
    public void deleteUser(int id){
        usersRepository.delete(usersRepository.findById(id).get());
    }
    public UserEntity editUser(int id,UserDTO user){
        UserEntity editedUser = usersRepository.findById(id).get();
        editedUser.setEmail(user.getEmail());
        editedUser.setAccesslevel(user.getAccesslevel());
        editedUser.setUsername(user.getUsername());
        editedUser.setPassword(user.getPassword());
        return usersRepository.save(editedUser);
    }
    public UserEntity userById(int id){
        return usersRepository.findById(id).get();
    }

}
