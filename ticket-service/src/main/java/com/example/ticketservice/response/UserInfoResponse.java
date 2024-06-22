package com.example.ticketservice.response;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoResponse {
    Integer id;
    String nickname;
    String email;
    Date created;
}
