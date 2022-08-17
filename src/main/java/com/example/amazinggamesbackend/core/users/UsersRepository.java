package com.example.amazinggamesbackend.core.users;

import com.example.amazinggamesbackend.core.users.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Integer> {
}
