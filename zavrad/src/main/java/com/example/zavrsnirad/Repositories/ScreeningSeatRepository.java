package com.example.zavrsnirad.Repositories;

import com.example.zavrsnirad.Models.Reservation;
import com.example.zavrsnirad.Models.Screeningseat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Transactional
@Repository
public interface ScreeningSeatRepository extends JpaRepository<Screeningseat, Integer> {
    List<Screeningseat> findByScreeningId(Integer screeningId);

    @Modifying
    @Query(value= "update Screeningseat s set s.status = true where s.hallSeat.id =:hallSeatId and s.screening.id =:screeningId")
    public void updateOccupation(@Param("hallSeatId") Integer hallSeatId, @Param("screeningId") Integer screeningId);
    @Modifying
    @Query(value= "update Screeningseat s set s.reservation =:reservation where s.hallSeat.id =:hallSeatId and s.screening.id =:screeningId")
    void updateReservation(@Param("hallSeatId")Integer hallSeatId, @Param("screeningId")int screeningId, @Param("reservation") Reservation reservation);

    int countByReservationId(Integer id);

    @Modifying
    @Query(value= "update Screeningseat s set s.status = false, s.reservation = NULL where s.reservation.id =:reservationId")
    void unReserveSeats(@Param("reservationId")int reservationId);
}
