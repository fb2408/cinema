package com.example.zavrsnirad.Services;

import com.example.zavrsnirad.Models.CinemaHall;
import com.example.zavrsnirad.Repositories.CinemaHallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaHallService {
    @Autowired
    private CinemaHallRepository cinemaHallRepository;
    public List<CinemaHall> findByCinemaId(int cinemaId) {
        return cinemaHallRepository.findByCinemaId(cinemaId);
    }
}
