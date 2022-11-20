package com.example.zavrsnirad.Repositories;

import com.example.zavrsnirad.Models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    boolean existsByReservationId(Integer id);

    Payment findByReservationId(int reservationId);
}
