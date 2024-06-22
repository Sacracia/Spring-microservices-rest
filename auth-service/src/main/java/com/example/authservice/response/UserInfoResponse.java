package com.example.authservice.response;

import com.example.authservice.model.AppUser;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfoResponse {
    Integer id;
    String nickname;
    String email;
    Date created;
    public UserInfoResponse(AppUser user) {
        id = user.getId();
        nickname = user.getNickname();
        email = user.getEmail();
        created = user.getCreated();
    }
}
