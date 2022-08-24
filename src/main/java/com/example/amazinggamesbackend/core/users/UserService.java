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
    public UsersEntity editUser(int id,UsersDTO user){
        UsersEntity editedUser = usersRepository.findById(id).get();
        editedUser.setEmail(user.getEmail());
        editedUser.setAccesslevel(user.getAccesslevel());
        editedUser.setUsername(user.getUsername());
        editedUser.setPassword(user.getPassword());
        return usersRepository.save(editedUser);
    }
    public UsersEntity userById(int id){
        return usersRepository.findById(id).get();
    }

}
