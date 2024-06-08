package com.example.ticketservice.repository;

import com.example.ticketservice.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepo extends JpaRepository<Station, Integer> {
    Station findByStation(String station);
}
