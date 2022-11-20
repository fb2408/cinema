package com.example.zavrsnirad.Models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Table(name = "movie")
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 40)
    private String title;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "image", nullable = true)
    private String image;

    @Column(name = "release_date", nullable = false)
    private Date releaseDate;

    @Column(name = "start_screening_date", nullable = false)
    private Date startScreeningDate;

    @Column(name = "end_screening_date", nullable = false)
    private Date endScreeningDate;

}