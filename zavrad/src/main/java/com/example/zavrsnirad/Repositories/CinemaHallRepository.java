package com.example.zavrsnirad.Repositories;

import com.example.zavrsnirad.Models.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Integer> {
    List<CinemaHall> findByCinemaId(int cinemaId);
}
