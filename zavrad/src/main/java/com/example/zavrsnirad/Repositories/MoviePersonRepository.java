package com.example.zavrsnirad.Repositories;

import com.example.zavrsnirad.Models.MoviePerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MoviePersonRepository extends JpaRepository<MoviePerson, Integer> {
    @Query( value="SELECT * FROM movie_person u where u.movie_id= :movieId", nativeQuery = true)
    List<MoviePerson> findByMovieId(@Param("movieId")Integer movieId);
}
