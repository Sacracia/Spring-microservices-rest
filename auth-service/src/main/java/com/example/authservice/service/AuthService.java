package com.example.authservice.service;

import com.example.authservice.repository.UserRepo;
import com.example.authservice.model.AppUser;
import com.example.authservice.response.UserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AuthService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    SessionService sessionService;

    @Autowired
    private JwtService jwtService;

    public List<AppUser> getUsers() {
        return userRepo.findAll();
    }

    public String signUp(String nickname, String email, String password) throws Exception {
        if (nickname.isEmpty() || email.isEmpty() || password.isEmpty())
            throw new Exception("Data cannot be empty");

        // Pattern checks
        Pattern emailPattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$");
        if (!emailPattern.matcher(email).matches())
            throw new Exception("Invalid email format");
        if (!passwordPattern.matcher(password).matches())
            throw new Exception("Password does not meet requirements");

        // Existence check
        if (userRepo.existsByNickname(nickname))
            throw new Exception("User " + nickname + " already exists");
        if (userRepo.existsByEmail(email))
            throw new Exception("Email " + email + " already used");

        // Save
        AppUser appUser = new AppUser();
        appUser.setEmail(email);
        appUser.setPassword(password);
        appUser.setNickname(nickname);
        userRepo.save(appUser);
        return "Successfully registered";
    }

    public String singIn(String email, String password) throws Exception {
        Optional<AppUser> result = userRepo.findByEmail(email);
        if (result.isEmpty())
            throw new Exception("User not found");
        if (!Objects.equals(result.get().getPassword(), password))
            throw new Exception("Incorrect password");
        return sessionService.setSessionForUser(result.get());
    }

    public UserInfoResponse getUserByToken(String token) throws Exception {
        String email = jwtService.extractAllClaims(token).getSubject();
        if (email == null)
            throw new Exception();
        Optional<AppUser> user = userRepo.findByEmail(email);
        if (user.isEmpty())
            throw new Exception();
        return new UserInfoResponse(user.get());
    }

    public Boolean isTokenValid(String token) {
        try {
            return !jwtService.isExpired(token);
        }
        catch (Exception ex) {
            return Boolean.FALSE;
        }
    }
}
