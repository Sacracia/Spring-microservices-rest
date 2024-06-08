package com.example.ticketservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Schema(description = "сущность заказа")
@Table(name="order_tbl")
@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer user_id;
    @Column(nullable = false)
    private Integer from_station_id;
    @Column(nullable = false)
    private Integer to_station_id;
    @Column(nullable = false)
    private Integer status = 1;
    private Date created = new Timestamp(System.currentTimeMillis());
}
