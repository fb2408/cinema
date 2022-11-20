package com.example.zavrsnirad.Repositories;

import com.example.zavrsnirad.Models.Hallseat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallSeatRepository extends JpaRepository<Hallseat, Integer> {
    @Query(value= "select * from hallSeat e  where e.cinema_hall_id =:cinemaId",nativeQuery=true)
    List<Hallseat> findByCinemaHallId(@Param("cinemaId") int cinemaHallId);

    Hallseat findByCinemaHallIdAndRowNumberAndColumnNumber(Integer id, int row, int column);

}
