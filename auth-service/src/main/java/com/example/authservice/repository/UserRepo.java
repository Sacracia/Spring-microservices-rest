package com.example.authservice.repository;

import com.example.authservice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser, Integer> {
    public Boolean existsByNickname(String nickname);

    public Boolean existsByEmail(String email);

    public Optional<AppUser> findByEmail(String email);
}
