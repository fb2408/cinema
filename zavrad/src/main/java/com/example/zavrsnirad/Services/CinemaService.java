package com.example.zavrsnirad.Services;

import com.example.zavrsnirad.Models.Cinema;
import com.example.zavrsnirad.Repositories.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;

    public List<Cinema> listAll() {
        return cinemaRepository.findAll();
    }
}
