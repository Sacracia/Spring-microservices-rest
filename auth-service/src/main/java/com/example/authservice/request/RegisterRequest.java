package com.example.authservice.request;

import lombok.Data;

@Data
public class RegisterRequest {
    String nickname;
    String email;
    String password;
}
