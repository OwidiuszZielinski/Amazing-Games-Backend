package com.example.amazinggamesbackend.core.users;

import com.example.amazinggamesbackend.core.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByUsernameIgnoreCase(String username);
    Optional<UserEntity> findByEmail(String username);

    Optional<UserEntity> findById(Integer id);
}
