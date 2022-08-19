package com.example.amazinggamesbackend.core.users;

import com.example.amazinggamesbackend.core.users.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Integer> {
    List<UsersEntity> findByUsername(String username);
}
