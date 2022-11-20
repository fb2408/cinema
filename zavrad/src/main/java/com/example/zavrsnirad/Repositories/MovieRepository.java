package com.example.zavrsnirad.Repositories;

import com.example.zavrsnirad.Models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

}
