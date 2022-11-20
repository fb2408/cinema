package com.example.zavrsnirad.Repositories;

import com.example.zavrsnirad.Models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
