package com.example.zavrsnirad.Models;

import javax.persistence.*;

@Table(name = "screeningseat")
@Entity
public class Screeningseat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screening_seat_id", nullable = false)
    private Integer id;

    @Column(name = "is_occupied", nullable = false)
    private Boolean status = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hall_seat_id", nullable = false)
    private Hallseat hallSeat;

    @ManyToOne(optional = false)
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reservation_id", nullable = true)
    private Reservation reservation;

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public Hallseat getHallSeat() {
        return hallSeat;
    }

    public void setHallSeat(Hallseat hallSeat) {
        this.hallSeat = hallSeat;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}