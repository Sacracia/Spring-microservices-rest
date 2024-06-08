package com.example.authservice.repository;

import com.example.authservice.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepo extends JpaRepository<Session, Integer> {
    Optional<Session> findSessionByUserId(Integer user_id);
}
