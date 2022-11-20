package com.example.zavrsnirad.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "cinema_hall")
@Entity
@Getter
@Setter
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinema_hall_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "total_seats", nullable = false)
    private int totalSeats;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

}