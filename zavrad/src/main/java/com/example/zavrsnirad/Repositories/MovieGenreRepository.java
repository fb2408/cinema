package com.example.zavrsnirad.Repositories;

import com.example.zavrsnirad.Models.Moviegenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieGenreRepository extends JpaRepository<Moviegenre, Integer> {
    List<Moviegenre> findByMovieId(Integer id);

    void deleteByMovieIdAndGenreId(int movieId, int genreId);

    List<Moviegenre> findByGenreId(int id);
}
