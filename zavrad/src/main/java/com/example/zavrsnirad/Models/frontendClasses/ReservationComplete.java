package com.example.zavrsnirad.Models.frontendClasses;

import com.example.zavrsnirad.Models.Movie;
import com.example.zavrsnirad.Models.Screening;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
public class ReservationComplete {
    public Integer getReservationId() {
        return reservationId;
    }

    public boolean isPaid() {
        return paid;
    }

    public List<Integer> getStars() {
        return stars;
    }

    public Screening getScreening() {
        return screening;
    }

    public Movie getMovie() {
        return movie;
    }

    public Integer getNumOfSeats() { return numOfSeats;}

    public Integer numOfSeats;

    private Integer reservationId;
    private boolean paid;
    private List<Integer> stars;
    private Screening screening;
    private Movie movie;

    public void setNumOfSeats(Integer numOfSeats) { this.numOfSeats = numOfSeats;}

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setStars(List<Integer> stars) {
        this.stars = stars;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
