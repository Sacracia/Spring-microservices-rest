package com.example.ticketservice.request;

import lombok.Data;

@Data
public class CreateOrderRequest {
    String token;
    String from;
    String to;
}
