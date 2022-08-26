package com.example.amazinggamesbackend.core.users;

import com.example.amazinggamesbackend.core.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity,Integer> {
    List<UserEntity> findByUsername(String username);
}
