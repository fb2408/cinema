package com.example.zavrsnirad.Services;

import com.example.zavrsnirad.Models.MoviePerson;
import com.example.zavrsnirad.Repositories.MoviePersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoviePersonService {
    @Autowired
    private MoviePersonRepository moviePersonRepository;

    public List<MoviePerson> findByMovieId(Integer id) {
        return moviePersonRepository.findByMovieId(id);
    }
}
