package com.example.zavrsnirad.Services;

import com.example.zavrsnirad.Models.Genre;
import com.example.zavrsnirad.Models.Moviegenre;
import com.example.zavrsnirad.Repositories.MovieGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieGenreService {
    @Autowired
    private MovieGenreRepository movieGenreRepository;

    public List<Moviegenre> listAll() {
        return movieGenreRepository.findAll();
    }
    public List<Moviegenre> getByMovieId(Integer movieId) {
        return movieGenreRepository.findByMovieId(movieId);
    }

    public void deleteById(Integer id) {
        movieGenreRepository.deleteById(id);
    }

    public void update(Moviegenre mg) {
        movieGenreRepository.save(mg);
    }

    public void deleteByMovieIdAndGenreId(int movieId, int genreId) {
        movieGenreRepository.deleteByMovieIdAndGenreId(movieId, genreId);
    }

    public List<Moviegenre> getByGenreId(int id) {
        return movieGenreRepository.findByGenreId(id);
    }

    public List<Moviegenre> findByMovieId(Integer id) {
        return movieGenreRepository.findByMovieId(id);
    }
}
