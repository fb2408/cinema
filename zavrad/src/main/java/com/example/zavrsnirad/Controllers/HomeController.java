package com.example.zavrsnirad.Controllers;

import com.example.zavrsnirad.Models.Cinema;
import com.example.zavrsnirad.Models.Movie;
import com.example.zavrsnirad.Models.UserModel;
import com.example.zavrsnirad.Services.CinemaService;
import com.example.zavrsnirad.Services.CityService;
import com.example.zavrsnirad.Services.MovieService;
import com.example.zavrsnirad.Services.UserService;
import com.example.zavrsnirad.dto.RegisterFormDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
@AllArgsConstructor
@NoArgsConstructor
public class HomeController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @RequestMapping(value="/loginRouter", method = RequestMethod.GET)
    public String loginRouter(HttpServletRequest req) {
        if(req.getUserPrincipal() == null) return "redirect:/";
        if (req.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin";
        } else {
            return "redirect:/profile";
        }
    }

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String index(Model model, Authentication auth) {
        List<Movie> listOfMovies = movieService.listAll();
        List<Movie> threeCards = new ArrayList<>();
        List<List<Movie>> previewMoviesCards = new ArrayList<>();
        List<Movie> firstCards= new ArrayList<>();
        int counter = 0;
        boolean firstAdded = false;
        for(Movie m : listOfMovies) {
            LocalDate relDate = m.getReleaseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate startDate = m.getStartScreeningDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            System.out.println(m.getId());
            if(m.getReleaseDate() != null && relDate.compareTo(LocalDate.now()) < 0 && LocalDate.now().compareTo(startDate) < 0) {
                System.out.println(relDate);
                System.out.println(startDate);
                if(counter < 3) {
                    threeCards.add(m);
                } else {
                    if(firstAdded) {
                        previewMoviesCards.add(threeCards);
                    } else {
                        firstCards = threeCards;
                    }
                    firstAdded = true;
                    threeCards = new ArrayList<>();
                    threeCards.add(m);
                    counter = 0;
                }
                counter++;
            }
        }
        if(threeCards.size() == 1) {
            threeCards.add(previewMoviesCards.get(0).get(0));
        } else if(threeCards.size() == 2) {
            threeCards.add(previewMoviesCards.get(0).get(0));
            threeCards.add(previewMoviesCards.get(0).get(1));
        }
        previewMoviesCards.add(threeCards);

        List<Cinema> listOfCinemas = cinemaService.listAll();
        if(auth != null) {
            model.addAttribute("loggedIn", true);
        } else {
            model.addAttribute("loggedIn", false);
        }

        model.addAttribute("firstCards", firstCards);
        model.addAttribute("previewMovies", previewMoviesCards);
        model.addAttribute("listOfMovies", listOfMovies);
        model.addAttribute("listOfCinemas", listOfCinemas);
        model.addAttribute("loggedIn", false);

        model.addAttribute("registerForm", new RegisterFormDTO());
        model.addAttribute("listOfCities", cityService.listAll());
        return "homePage.html";
    };

    @PostMapping("/")
    public String register(RegisterFormDTO registerFormDTO, ModelMap model, HttpServletRequest request) {
        UserModel registered;
        registered = userService.registerNewUser(registerFormDTO);
        return "redirect:/";
    }
}
