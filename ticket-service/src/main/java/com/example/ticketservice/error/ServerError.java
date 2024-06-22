package com.example.ticketservice.error;

public class ServerError extends Exception{
    public ServerError(String message) {
        super(message);
    }
}
