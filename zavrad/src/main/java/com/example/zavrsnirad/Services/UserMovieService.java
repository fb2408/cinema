package com.example.zavrsnirad.Services;

import com.example.zavrsnirad.Models.UserMovie;
import com.example.zavrsnirad.Repositories.UserMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMovieService {
    @Autowired
    private UserMovieRepository userMovieRepository;

    public boolean checkIfExistByUserIdAndMovieId(Integer id, Integer id1) {
        return userMovieRepository.existsByUserIdAndMovieId(id, id1);
    }

    public UserMovie findByUserIdAndMovieId(Integer id, Integer id1) {
        return userMovieRepository.findByUserIdAndMovieId(id, id1);
    }

    public void save(UserMovie userMovie) {
        userMovieRepository.save(userMovie);
    }
}
