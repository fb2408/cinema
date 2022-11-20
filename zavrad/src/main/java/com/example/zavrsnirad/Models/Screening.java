package com.example.zavrsnirad.Models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "screening", indexes = {
        @Index(name = "screening_date_of_screening_key", columnList = "date_of_screening", unique = true),
        @Index(name = "screening_movie_id_key", columnList = "movie_id", unique = true),
        @Index(name = "screening_cinema_hall_id_key", columnList = "cinema_hall_id", unique = true)
})
@Entity
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screening_id", nullable = false)
    private Integer id;

    @Column(name = "date_of_screening", nullable = false)
    private LocalDate dateOfScreening;

    @Column(name = "start_screening_time", nullable = false)
    private LocalTime startScreeningTime;

    @Column(name = "end_screening_time", nullable = false)
    private LocalTime endScreeningTime;

    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cinema_hall_id", nullable = false)
    private CinemaHall cinemaHall;

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalTime getEndScreeningTime() {
        return endScreeningTime;
    }

    public void setEndScreeningTime(LocalTime endScreeningTime) {
        this.endScreeningTime = endScreeningTime;
    }

    public LocalTime getStartScreeningTime() {
        return startScreeningTime;
    }

    public void setStartScreeningTime(LocalTime startScreeningTime) {
        this.startScreeningTime = startScreeningTime;
    }

    public LocalDate getDateOfScreening() {
        return dateOfScreening;
    }

    public void setDateOfScreening(LocalDate dateOfScreening) {
        this.dateOfScreening = dateOfScreening;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}