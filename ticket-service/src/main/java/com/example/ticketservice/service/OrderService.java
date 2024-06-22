package com.example.ticketservice.service;

import com.example.ticketservice.error.ServerError;
import com.example.ticketservice.model.Order;
import com.example.ticketservice.model.Station;
import com.example.ticketservice.repository.OrderRepo;
import com.example.ticketservice.repository.StationRepo;
import com.example.ticketservice.response.UserInfoResponse;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    StationRepo stationRepo;

    public String createOrder(String token, String from, String to) throws Exception {
        if (Objects.equals(from, to))
            throw new Exception("Departure must differ from destination");
        Boolean isValid;
        try {
            isValid = isTokenValid(token);
        }
        catch (Exception ex) {
            throw new ServerError("Unable to reach auth-service");
        }
        if (!isValid)
            throw new Exception("Invalid token");
        Station departure = stationRepo.findByStation(from);
        if (departure == null)
            throw new Exception("Invalid departure " + from);
        Station destination = stationRepo.findByStation(to);
        if (destination == null)
            throw new Exception("Invalid destination " + to);
        UserInfoResponse userInfo = getUserInfoByToken(token);
        if (userInfo == null)
            throw new Exception("Cannot get user info by token");
        Order order = new Order();
        order.setUser_id(userInfo.getId());
        order.setFrom_station_id(departure.getId());
        order.setTo_station_id(destination.getId());
        orderRepo.save(order);
        return "OK";
    }

    public Order getOrderInfo(Integer id) throws Exception {
        Optional<Order> order = orderRepo.findById(id);
        if (order.isEmpty())
            throw new Exception("Unable to get order by id");
        return order.get();
    }

    private Boolean isTokenValid(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request =
                new HttpEntity<>(String.format("{\"token\": \"%s\"}", token), headers);

        String personResultAsJsonStr =
                restTemplate.postForObject("http://auth:9000/auth/token/valid", request, String.class);
        return Objects.equals(personResultAsJsonStr, "true");
    }

    private UserInfoResponse getUserInfoByToken(String token) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request =
                    new HttpEntity<>(String.format("{\"token\": \"%s\"}", token), headers);
            return restTemplate.postForObject("http://auth:9000/auth/token/info"
                    , request, UserInfoResponse.class);
        }
        catch (Exception ex) {
            return null;
        }
    }
}
