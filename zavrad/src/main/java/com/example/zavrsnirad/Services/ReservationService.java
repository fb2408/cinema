package com.example.zavrsnirad.Services;

import com.example.zavrsnirad.Models.Reservation;
import com.example.zavrsnirad.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public List<Reservation> findReservationByUserId(Integer id) {
        return reservationRepository.findByUserId(id);
    }

    public void deleteByReservationId(int reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public Reservation findById(int reservationId) throws Exception {
        return reservationRepository.findById(reservationId).orElseThrow(() -> new Exception("Reservation not founded"));
    }
}
