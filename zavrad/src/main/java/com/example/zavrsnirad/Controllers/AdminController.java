package com.example.zavrsnirad.Controllers;


import com.example.zavrsnirad.Models.Genre;
import com.example.zavrsnirad.Models.Movie;
import com.example.zavrsnirad.Models.Moviegenre;
import com.example.zavrsnirad.Services.GenreService;
import com.example.zavrsnirad.Services.MovieGenreService;
import com.example.zavrsnirad.Services.MovieService;
import com.example.zavrsnirad.Services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
@NoArgsConstructor
public class AdminController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private GenreService genreService;

    @Autowired
    private MovieGenreService movieGenreService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "")
    public String adminIndex(Model model, Authentication auth) {
        if(auth == null) {
            return "redirect:/?NotLoggedIn=true";
        } else {
            model.addAttribute("loggedIn", true);
            model.addAttribute("user", userService.findUserByEmail(auth.getName()));
            return "AdminPage";
        }

    }


    @RequestMapping(value = "/{name}/", method = RequestMethod.GET)
    public String listingFunction(Model model, @PathVariable String name,
                                  @RequestParam(name = "atr", defaultValue = "none") String atr,
                                  @RequestParam(name = "sort", defaultValue = "none") String sort,
                                  @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "9") Integer pageSize) {
        if(name.equals("genres")) {
            List<Genre> listOfGenres = genreService.listAll();
            model.addAttribute("listOfGenres", listOfGenres);
            return "Genres.html";
        }

        List<Movie> listOfMovies;
        List<Moviegenre> listOfMovieGenre = movieGenreService.listAll();
        List<Genre> listOfGenreForMovie;
        List<Genre> listOfGenres = genreService.listAll();
        Map<Movie,List<Genre>> sendMap = new HashMap<>();
        listOfMovies = movieService.listAll();
        for(Movie m : listOfMovies) {
            listOfGenreForMovie = new ArrayList<>();
            for(Moviegenre mg : listOfMovieGenre) {
                if(mg.getMovie().getId() == m.getId()) {
                    listOfGenreForMovie.add(mg.getGenre());
                    System.out.println("added " + mg.getGenre().getName());
                }
            }
            sendMap.put(m, listOfGenreForMovie);
        }
        for(Map.Entry<Movie,List<Genre>> entry : sendMap.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

        if(!sort.equals("none")) { //sorting included

        }

        model.addAttribute("mapOfMovies", sendMap);
        model.addAttribute("listOfGenres", listOfGenres);
        return "Movies";
        //}

    }


    //show, add and delete functions for genres of movies
    @SneakyThrows
    @RequestMapping(value = "/movies/{id}/genres/", method = RequestMethod.GET)
    public String showMovieGenres(Model model, @PathVariable String id) {
        List<Moviegenre> listOfMovieGenre = movieGenreService.getByMovieId(Integer.parseInt(id));
        List<Genre> listOfGenreForMovie = new ArrayList<>();
        for(Moviegenre mg : listOfMovieGenre) {
            if(mg.getMovie().getId() == Integer.parseInt(id)) {
                listOfGenreForMovie.add(mg.getGenre());
                System.out.println("added genre" + mg.getGenre().getName());
            }
        }
        Movie m = movieService.findMovie(Integer.parseInt(id));
        List<Genre> listOfGenre = genreService.listAll();
        model.addAttribute("listOfGenres", listOfGenre);
        model.addAttribute("movie", m);
        model.addAttribute("listOfGenreForMovie", listOfGenreForMovie);
        return "MovieGenres.html";
    }

    @RequestMapping(value = "/movies/{id}/genres/add/", method = RequestMethod.POST)
    public String addMovieGenres(Model model, @RequestParam(name ="genreId") String genreId, @PathVariable String id) {
        Moviegenre mg = new Moviegenre();
        try {
            Movie movie = movieService.findMovie(Integer.parseInt(id));
            mg.setMovie(movie);
        } catch(Exception exc) {
            System.out.println("Movie not found");
        }
        try {
            Genre genre = genreService.findGenre(Integer.parseInt(genreId));
            mg.setGenre(genre);
        } catch(Exception exc) {
            System.out.println("Genre not found");
        }
        try {
            movieGenreService.update(mg);
        } catch (Exception exc){
            System.out.println("Could not add this genre to this movie");
        }
        System.out.println("Genre for movie successfully added");
        return "redirect:/admin/movies/{id}/genres/";
    }

    @Transactional
    @RequestMapping(value = "/movies/{id}/genres/delete/{genreId}", method = RequestMethod.GET)
    public String deleteMovieGenres(Model model, @PathVariable String id, @PathVariable String genreId) throws Exception {
        movieGenreService.deleteByMovieIdAndGenreId(Integer.parseInt(id), Integer.parseInt(genreId));
        System.out.println("Genre of movie successfully deleted");
        return "redirect:/admin/movies/{id}/genres/";
    }

    //add, delete and edit functions for movies
    @RequestMapping(value = "/movies/add/", method = RequestMethod.POST)
    public String addMovie(@RequestParam(name = "title") String title,
                           @RequestParam(name = "duration") String duration,
                           @RequestParam(name = "description") String description,
                           @RequestParam(name = "releaseDate", defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date releaseDate,
                           @RequestParam(name = "endScreeningDate", defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endScreeningDate,
                           @RequestParam(name = "startScreeningDate", defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startScreeningDate
                           ) {
        Movie m = new Movie();
        m.setDescription(description);
        m.setDuration(duration);
        m.setEndScreeningDate(endScreeningDate);
        m.setStartScreeningDate(startScreeningDate);
        m.setReleaseDate(releaseDate);
        m.setTitle(title);
        movieService.updateMovie(m);
        return "redirect:/admin/movies/";
    }

    @RequestMapping(value = "/movies/delete/{id}", method = RequestMethod.GET)
    public String deleteMovie(@PathVariable String id) {
        List<Moviegenre> movieGenres = movieGenreService.getByMovieId(Integer.parseInt(id));
        for(Moviegenre mg : movieGenres) movieGenreService.deleteById(mg.getId());
        movieService.deleteMovie(Integer.parseInt(id));
        return "redirect:/admin/movies/";
    }

    @RequestMapping(value = "/movies/edit/", method = RequestMethod.POST)
    public String editMovie(@RequestParam(name = "id") String id,
                            @RequestParam(name = "title") String title,
                            @RequestParam(name = "duration") String duration,
                            @RequestParam(name = "description") String description,
                            @RequestParam(name = "releaseDate", defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date releaseDate,
                            @RequestParam(name = "endScreeningDate", defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endScreeningDate,
                            @RequestParam(name = "startScreeningDate", defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startScreeningDate) {
        Movie m = new Movie();
        m.setId(Integer.parseInt(id));
        m.setTitle(title);
        m.setDescription(description);
        m.setDuration(duration);
        m.setReleaseDate(releaseDate);
        m.setEndScreeningDate(endScreeningDate);
        m.setStartScreeningDate(startScreeningDate);
        movieService.updateMovie(m);
        return "redirect:/admin/movies/";
    }




    @RequestMapping(value = "/genres/add/", method = RequestMethod.POST)
    public String addGenre(@RequestParam(name = "name") String name) {
        Genre g = new Genre();
        g.setName(name);
        genreService.updateGenre(g);
        return "redirect:/admin/genres/";
    }

    @RequestMapping(value = "/genres/delete/{id}", method = RequestMethod.GET)
    public String deleteGenre( @PathVariable String id) {
        List<Moviegenre> movieGenres = movieGenreService.getByGenreId(Integer.parseInt(id));
        for(Moviegenre mg : movieGenres) movieGenreService.deleteById(mg.getId());
        genreService.deleteGenre(Integer.parseInt(id));
        return "redirect:/admin/genres/";
    }

    @RequestMapping(value = "/genres/edit/", method = RequestMethod.POST)
    public String editGenre(@ModelAttribute("editGenre") Genre genre) {
        genreService.updateGenre(genre);
        return "redirect:/admin/genres/";
    }

}
