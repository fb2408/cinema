package com.example.zavrsnirad.Controllers;

import com.example.zavrsnirad.Models.*;
import com.example.zavrsnirad.Models.frontendClasses.MovieScreeningFront;
import com.example.zavrsnirad.Services.CinemaHallService;
import com.example.zavrsnirad.Services.MovieGenreService;
import com.example.zavrsnirad.Services.MoviePersonService;
import com.example.zavrsnirad.Services.ScreeningService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/cinema")
@AllArgsConstructor
public class CinemaController {

    @Autowired
    private ScreeningService screeningService;
    @Autowired
    private CinemaHallService cinemaHallService;
    @Autowired
    private MovieGenreService movieGenreService;
    @Autowired
    private MoviePersonService moviePersonService;

    @RequestMapping(value = "/{cinemaId}/", method = RequestMethod.GET)
    public String showAllMovies(Model model, @PathVariable String cinemaId,
                                @RequestParam(value = "numOfDay", defaultValue = "0") String numOfDay) {
        List<List<Screening>> currDaysGroupByList = new ArrayList<>();
        for(int i = 0 ; i < 7 ; i++) currDaysGroupByList.add(new ArrayList<>());
        List<CinemaHall> cinemaHalls = cinemaHallService.findByCinemaId(Integer.parseInt(cinemaId));
        List<Screening> activeScreeningsForCinema;
        for(CinemaHall cinemaHall : cinemaHalls) {
            activeScreeningsForCinema = screeningService.findByCinemaHallId(cinemaHall.getId());
            for(Screening sc : activeScreeningsForCinema) {
                LocalDate today = LocalDate.now();
                LocalDate endDate = LocalDate.now().plusDays(6);
                if(sc.getDateOfScreening().compareTo(endDate) <= 0) { //inside active week
//                    System.out.println("inside");
//                    System.out.println(sc.getDateOfScreening());
//                    System.out.println(endDate);
                    for(int i = 0 ; i < 7 ;  i ++) {
                        if(sc.getDateOfScreening().compareTo(today.plusDays(i)) == 0) {
                            currDaysGroupByList.get(i).add(sc);
                            break;
                        }
                    }
                } else {
//                    System.out.println("so far");
//                    System.out.println(sc.getDateOfScreening());
                }
            }
        }

        List<Screening> currTestingList = currDaysGroupByList.get(Integer.parseInt(numOfDay));
        List<MovieScreeningFront> showList = new ArrayList<>();
        for(Screening sc : currTestingList) {
            List<Moviegenre> movieGenres = movieGenreService.findByMovieId(sc.getMovie().getId());
            List<MoviePerson> moviePerson = moviePersonService.findByMovieId(sc.getMovie().getId());
            List<Genre> genres = new ArrayList<>();
            List<Person> actors = new ArrayList<>();
            Person director = new Person();
            movieGenres.forEach(mg -> genres.add(mg.getGenre()));
            for(MoviePerson mp : moviePerson) {
                if(mp.getType().equals("Director")) {
                    director = mp.getPerson();
                } else {
                    actors.add(mp.getPerson());
                }
            }
            if(showList.size() > 0) {
                boolean founded = false;
                for(MovieScreeningFront msf : showList) {
                    if(msf.getMovie().getTitle().equals(sc.getMovie().getTitle())) {
                        msf.getScreening().add(sc);
                        founded = true;
                        break;
                    }
                }
                if(!founded) {
                    MovieScreeningFront msf = new MovieScreeningFront(sc.getMovie());
                    msf.setDirector(director);
                    msf.setActors(actors);
                    msf.setGenres(genres);
                    msf.getScreening().add(sc);
                    showList.add(msf);
                }
            } else {
                MovieScreeningFront msf = new MovieScreeningFront(sc.getMovie());
                msf.setDirector(director);
                msf.setActors(actors);
                msf.setGenres(genres);
                msf.getScreening().add(sc);
                showList.add(msf);

            }
        }
//        for(MovieScreeningFront msf : showList) {
//            System.out.println(msf.getMovie().getTitle());
//            for(Screening sc : msf.getScreening()) {
//                System.out.println(sc.getStartScreeningTime() + " " + sc.getDateOfScreening());
//            }
//        }

        //sent on backend list of day like MON TUE WED...
        List<String> listOfDaysShorter = new ArrayList<>();
        LocalDate currDay = LocalDate.now();
        DayOfWeek currDOW;
        for(int i = 0 ; i < 7; i++) {
            currDOW = currDay.plusDays(i).getDayOfWeek();
            listOfDaysShorter.add(currDOW.toString().substring(0, 3));

        }

        model.addAttribute("listOfDays", listOfDaysShorter);
        model.addAttribute("dayMovies", showList);
        model.addAttribute("active", Integer.parseInt(numOfDay));
        return "Cinema.html";
    }
}
