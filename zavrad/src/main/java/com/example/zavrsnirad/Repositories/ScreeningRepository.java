package com.example.zavrsnirad.Repositories;

import com.example.zavrsnirad.Models.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;


public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    List<Screening> findByCinemaHallId(Integer id);
}
