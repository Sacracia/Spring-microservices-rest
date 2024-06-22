package com.example.authservice.service;

import com.example.authservice.model.AppUser;
import com.example.authservice.model.Session;
import com.example.authservice.repository.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class SessionService {
    @Autowired
    SessionRepo sessionRepo;

    @Autowired
    JwtService jwtService;

    String setSessionForUser(AppUser user) {
        Optional<Session> session = sessionRepo.findSessionByUserId(user.getId());
        String token = jwtService.generateToken(user);
        if (session.isEmpty()) {
            Session newSession = new Session();
            newSession.setUserId(user.getId());
            newSession.setExpires(new Timestamp(jwtService.extractAllClaims(token).getExpiration().getTime()));
            newSession.setToken(token);
            sessionRepo.save(newSession);
        }
        else {
            Session oldSession = session.get();
            oldSession.setExpires(new Timestamp(jwtService.extractAllClaims(token).getExpiration().getTime()));
            sessionRepo.save(oldSession);
        }
        return token;
    }
}
