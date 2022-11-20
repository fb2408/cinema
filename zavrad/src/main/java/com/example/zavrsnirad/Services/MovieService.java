package com.example.zavrsnirad.Services;

import com.example.zavrsnirad.Models.Movie;
import com.example.zavrsnirad.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    public List<Movie> listAll() {
        return movieRepository.findAll();
    }
    public void deleteMovie(Integer id) {
        movieRepository.deleteById(id);
        System.out.println("Movie deleted" + id);
    }

    public void updateMovie(Movie movie) {
        movieRepository.save(movie);
        System.out.println("Updated movie" + movie.getId());
    }

    public Movie findMovie(int id) throws Exception {
        return movieRepository.findById(id).orElseThrow(() -> new Exception("Movie not found"));
    }
}
