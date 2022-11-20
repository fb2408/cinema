package com.example.zavrsnirad.Services;

import com.example.zavrsnirad.Models.Genre;
import com.example.zavrsnirad.Repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> listAll() {
        return genreRepository.findAll();
    }

    public void deleteGenre(int id) {
        genreRepository.deleteById(id);

    }

    public void updateGenre(Genre genre) {
        genreRepository.save(genre);
    }

    public Genre findGenre(int id) throws Exception {
        return genreRepository.findById(id).orElseThrow(() -> new Exception("Genre not found"));
    }
}
