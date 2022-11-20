package com.example.zavrsnirad.Services;

import com.example.zavrsnirad.Models.Reservation;
import com.example.zavrsnirad.Models.Screeningseat;
import com.example.zavrsnirad.Repositories.ScreeningSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreeningSeatService {
    @Autowired
    private ScreeningSeatRepository screeningSeatRepository;

    public List<Screeningseat> findByScreening(Integer screeningId) {
        return screeningSeatRepository.findByScreeningId(screeningId);
    }

    public void updateOccupation(Integer hallSeatId, Integer screeningId) {
        System.out.println(hallSeatId + "," + screeningId);
        screeningSeatRepository.updateOccupation(hallSeatId, screeningId);
    }

    public void updateReservation(Integer hallSeatId, int screeningId, Reservation reservation) {
        screeningSeatRepository.updateReservation(hallSeatId, screeningId, reservation);
    }

    public int countByReservationId(Integer id) {
        return screeningSeatRepository.countByReservationId(id);
    }

    public void unReserve(int reservationId) {
        screeningSeatRepository.unReserveSeats(reservationId);
    }
}
