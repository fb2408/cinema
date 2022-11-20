package com.example.zavrsnirad.Models;

import javax.persistence.*;

@Table(name = "hallseat", indexes = {
        @Index(name = "hallseat_column_number_key", columnList = "column_number", unique = true),
        @Index(name = "hallseat_row_number_key", columnList = "row_number", unique = true),
        @Index(name = "hallseat_cinema_hall_id_key", columnList = "cinema_hall_id", unique = true)
})
@Entity
public class Hallseat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hall_seat_id", nullable = false)
    private Integer id;

    @Column(name = "row_number", nullable = false)
    private Integer rowNumber;

    @Column(name = "column_number", nullable = false)
    private Integer columnNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cinema_hall_id", nullable = false)
    private CinemaHall cinemaHall;

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    public Integer getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}