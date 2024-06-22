package com.example.authservice.controller;

import com.example.authservice.request.LoginRequest;
import com.example.authservice.request.RegisterRequest;
import com.example.authservice.request.TokenRequest;
import com.example.authservice.model.AppUser;
import com.example.authservice.response.UserInfoResponse;
import com.example.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Operation(summary = "получение всех пользователей")
    @GetMapping("users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok(authService.getUsers());
    }

    @Operation(summary = "регистрация")
    @PostMapping("register")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest request) {
        try {
            String result = authService.signUp(request.getNickname(),
                    request.getEmail(), request.getPassword());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "авторизация")
    @PostMapping("login")
    public ResponseEntity<String> signIn(@RequestBody LoginRequest request) {
        try {
            String token = authService.singIn(request.getEmail(), request.getPassword());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "получение инфрмации о пользователе по токену")
    @PostMapping("token/info")
    public ResponseEntity<UserInfoResponse> getUserByToken(@RequestBody TokenRequest request) {
        try {
            UserInfoResponse response = authService.getUserByToken(request.getToken());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "проверка токена на валидность")
    @PostMapping("token/valid")
    public ResponseEntity<Boolean> isTokenValid(@RequestBody TokenRequest request) {
        Boolean isValid = authService.isTokenValid(request.getToken());
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }
}
