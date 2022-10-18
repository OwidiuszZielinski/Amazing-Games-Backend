package com.example.amazinggamesbackend.core.users;

import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsersRepository usersRepository;
    public List<UserDTO> getAllUsers(){
        List<UserDTO> tempList = new ArrayList<>();
        for(UserEntity x : usersRepository.findAll()){
            tempList.add(UserDTO.fromWithoutPassword(x));

        }
        return tempList;
    }
    public void deleteUsers(List<Integer> ids){
        usersRepository.deleteAllByIdInBatch(ids);
    }
    public void updateUser(int id,UserDTO user){
        UserEntity update = userById(id);
        update.fromDTO(user);
        if(user.getPassword().isBlank()) {
            update.setPassword(update.getPassword());
        }
        else {
            update.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        usersRepository.save(update);
    }
    public UserEntity userById(int id) {
            return usersRepository.findById(id).get();
        }

}
