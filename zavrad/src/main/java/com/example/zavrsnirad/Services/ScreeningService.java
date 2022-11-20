package com.example.zavrsnirad.Services;

import com.example.zavrsnirad.Models.Screening;
import com.example.zavrsnirad.Repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {
    @Autowired
    private ScreeningRepository screeningRepository;

    public Screening findScreening(Integer screeningId) throws Exception {
        return screeningRepository.findById(screeningId).orElseThrow(() -> new Exception("Screening not found"));
    }

        public List<Screening> findByCinemaHallId(Integer id) {
        return screeningRepository.findByCinemaHallId(id);
    }
}
