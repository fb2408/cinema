package com.example.zavrsnirad.Models.frontendClasses;

import com.example.zavrsnirad.Models.Genre;
import com.example.zavrsnirad.Models.Movie;
import com.example.zavrsnirad.Models.Person;
import com.example.zavrsnirad.Models.Screening;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MovieScreeningFront {
    private Movie movie;
    private List<Screening> screening;
    private List<Genre> genres;
    private Person director;
    private List<Person> actors;

    public MovieScreeningFront(Movie m) {
        this.movie = m;
        this.screening = new ArrayList<>();

    }
}
