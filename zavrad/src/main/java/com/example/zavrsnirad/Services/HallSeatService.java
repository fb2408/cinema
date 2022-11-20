package com.example.zavrsnirad.Services;

import com.example.zavrsnirad.Models.Hallseat;
import com.example.zavrsnirad.Repositories.HallSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallSeatService {
    @Autowired
    private HallSeatRepository hallSeatRepository;

    public List<Hallseat> findByCinemaHall(Integer cinemaHallId) {
        return hallSeatRepository.findByCinemaHallId(cinemaHallId);
    }

    public Hallseat findByCinemaHallRowAndColumn(Integer id, int row, int column) {
        return hallSeatRepository.findByCinemaHallIdAndRowNumberAndColumnNumber(id, row, column);
    }
}
