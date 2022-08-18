package com.example.amazinggamesbackend.core.users;

import com.example.amazinggamesbackend.core.users.dto.UsersDTO;
import com.example.amazinggamesbackend.core.users.model.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public UsersEntity addUser(UsersDTO user){

        UsersEntity newuser = new UsersEntity();
        newuser.fromDTO(user);
        return usersRepository.save(newuser);
    }
    public List<UsersEntity> getAllUsers(){
        return usersRepository.findAll();
    }
    public void deleteUser(int id){
        usersRepository.delete(usersRepository.findById(id).get());
    }

}
