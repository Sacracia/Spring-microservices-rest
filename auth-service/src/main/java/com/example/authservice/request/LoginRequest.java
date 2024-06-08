package com.example.authservice.request;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}
