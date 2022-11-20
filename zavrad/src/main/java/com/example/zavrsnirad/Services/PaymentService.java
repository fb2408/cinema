package com.example.zavrsnirad.Services;

import com.example.zavrsnirad.Models.Payment;
import com.example.zavrsnirad.Repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public boolean existsByReservationId(Integer id) {
        return paymentRepository.existsByReservationId(id);
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    public Payment findByReservationId(int reservationId) {
        return paymentRepository.findByReservationId(reservationId);
    }

    public void delete(Payment payment) {
        paymentRepository.delete(payment);
    }
}
