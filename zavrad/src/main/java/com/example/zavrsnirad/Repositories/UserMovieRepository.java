package com.example.zavrsnirad.Repositories;

import com.example.zavrsnirad.Models.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMovieRepository extends JpaRepository<UserMovie, Integer> {
    boolean existsByUserIdAndMovieId(Integer id, Integer id1);

    UserMovie findByUserIdAndMovieId(Integer id, Integer id1);
}
