package com.example.ticketservice.controller;

import com.example.ticketservice.error.ServerError;
import com.example.ticketservice.model.Order;
import com.example.ticketservice.request.CreateOrderRequest;
import com.example.ticketservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Operation(summary = "создание заказа")
    @PostMapping("create")
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            String s = orderService.createOrder(request.getToken(), request.getFrom(), request.getTo());
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
        catch (ServerError error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "получение информации о заказе")
    @GetMapping("info/{order_id}")
    public ResponseEntity<Order> getOrderInfo(@PathVariable("order_id") Integer id) {
        try {
            return new ResponseEntity<>(orderService.getOrderInfo(id), HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
