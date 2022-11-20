package com.example.zavrsnirad.Controllers;


import com.example.zavrsnirad.Models.*;
import com.example.zavrsnirad.Models.frontendClasses.HallSeatComplete;
import com.example.zavrsnirad.Repositories.ReservationRepository;
import com.example.zavrsnirad.Services.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/movieSeatReservation")
@AllArgsConstructor
@NoArgsConstructor
public class ReservationController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private HallSeatService hallseatService;
    @Autowired
    private ScreeningSeatService screeningSeatService;
    @Autowired
    private ScreeningService screeningService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/{screeningId}/", method = RequestMethod.GET)
    public String showMovieSeatReservation(Model model, @PathVariable String screeningId, Authentication auth) {
        Screening screening = null;
        try {
            screening = screeningService.findScreening(Integer.parseInt(screeningId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert screening != null;
        Movie movie = screening.getMovie();
        CinemaHall cinemaHall= screening.getCinemaHall();
        List<Hallseat> seats = hallseatService.findByCinemaHall(cinemaHall.getId());
        seats.sort(new Comparator<Hallseat>() {
            @Override
            public int compare(Hallseat o1, Hallseat o2) {
                if(o1.getRowNumber() != o2.getRowNumber()) {
                    return o1.getRowNumber().compareTo(o2.getRowNumber()) > 0 ? 1 : -1;
                } else if(o1.getColumnNumber() != o2.getColumnNumber()) {
                     return o1.getColumnNumber().compareTo(o2.getColumnNumber()) > 0 ? 1 : -1;
                } else {
                    return 0;
                }
            }
        });
        //check list Of seats is sorted?
        for(Hallseat hallseat : seats) System.out.println("row" + hallseat.getRowNumber() + " , column" + hallseat.getColumnNumber());
        List<Screeningseat> listOfScreeningSeats = screeningSeatService.findByScreening(screening.getId());
        int noOfRows = seats.size() / 8;
        List<HallSeatComplete> seatsComplete = new ArrayList<>();
        for(Hallseat hallseat : seats) {
            for(Screeningseat screeningSeat : listOfScreeningSeats) {
                if(screeningSeat.getHallSeat().getId() == hallseat.getId()) {
                    seatsComplete.add(new HallSeatComplete(hallseat.getRowNumber(), hallseat.getColumnNumber(), screeningSeat.getStatus(), screeningSeat.getScreening().getId(),hallseat.getCinemaHall().getId(), String.valueOf(hallseat.getRowNumber() + "," + hallseat.getColumnNumber())));
                    break;
                }
            }
        }
        List<List<HallSeatComplete>> sendList = new ArrayList<>();
        for(int i = 0; i < noOfRows ; i++) {
            List<HallSeatComplete> currList = new ArrayList<>();
            for(int j = 0 ; j < 8 ; j++) {
                currList.add(seatsComplete.get(i*8 + j));
            }
            sendList.add(currList);
        }
        if(auth != null) {
            UserModel user = userService.findUserByEmail(auth.getName());
            model.addAttribute("currUser", user);
        }
        int moviePrice = screening.getPrice();
        String dateTime = "";
        dateTime += String.valueOf(screening.getDateOfScreening());
        dateTime += " ";
        dateTime += String.valueOf(screening.getStartScreeningTime());
        model.addAttribute("cinemaHall", cinemaHall);
        model.addAttribute("dateTime", dateTime);
        model.addAttribute("movie", movie);
        model.addAttribute("listOfSeats", sendList);
        model.addAttribute("screeningSeats", listOfScreeningSeats);
        model.addAttribute("moviePrice", moviePrice);
        return "reserveMovieSeat.html";
    }

    @RequestMapping(value = "/{screeningId}/reserve/{seats}", method = RequestMethod.GET)
    public String reserve(Model model, @PathVariable String screeningId, @PathVariable String seats, Authentication auth, RedirectAttributes redirectAttributes) {

        Screening screening = null;
        try {
            screening = screeningService.findScreening(Integer.parseInt(screeningId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert screening != null;
        UserModel user = userService.findUserByEmail(auth.getName());
        Reservation reservation = new Reservation();
        reservation.setScreening(screening);
        reservation.setUser(user);
        reservationService.saveReservation(reservation);

        CinemaHall cinemaHall = screening.getCinemaHall();
        String [] listOfSeats = seats.split("&");
        for(String s : listOfSeats) {
            int row = (Integer.parseInt(s) / 8) + 1;
            int column = (Integer.parseInt(s) % 8) + 1;
            Hallseat hallseat = hallseatService.findByCinemaHallRowAndColumn(cinemaHall.getId(), row, column);
            screeningSeatService.updateOccupation(hallseat.getId(), Integer.parseInt(screeningId));
            screeningSeatService.updateReservation(hallseat.getId(), Integer.parseInt(screeningId), reservation);
        }
        redirectAttributes.addFlashAttribute("newReservationId", reservation.getId());
        return "redirect:/profile";
    }

    @RequestMapping(value = "/{screeningId}/reserveAndPay/{seats}/", method = RequestMethod.GET)
    public String reserveAndPay(Model model, @PathVariable String screeningId, @PathVariable String seats, Authentication auth, RedirectAttributes redirectAttributes,
                                @RequestParam(name = "amount") String amount,
                                @RequestParam(name = "isIncluded") String isIncluded,
                                @RequestParam(name = "amountOfIncluded") String amountOfIncluded) {
        Screening screening = null;
        try {
            screening = screeningService.findScreening(Integer.parseInt(screeningId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert screening != null;
        UserModel user = userService.findUserByEmail(auth.getName());
        Reservation reservation = new Reservation();
        reservation.setScreening(screening);
        reservation.setUser(user);
        reservationService.saveReservation(reservation);
        CinemaHall cinemaHall = screening.getCinemaHall();
        String [] listOfSeats = seats.split("&");
        for(String s : listOfSeats) {
            int row = (Integer.parseInt(s) / 8) + 1;
            int column = (Integer.parseInt(s) % 8) + 1;
            Hallseat hallseat = hallseatService.findByCinemaHallRowAndColumn(cinemaHall.getId(), row, column);
            screeningSeatService.updateOccupation(hallseat.getId(), Integer.parseInt(screeningId));
            screeningSeatService.updateReservation(hallseat.getId(), Integer.parseInt(screeningId), reservation);
        }
        redirectAttributes.addFlashAttribute("newReservationId", reservation.getId());
        redirectAttributes.addFlashAttribute("amountOfPayment", Double.parseDouble(amount));
        redirectAttributes.addFlashAttribute("isIncludedBonusPoints", Boolean.valueOf(isIncluded));
        redirectAttributes.addFlashAttribute("amountOfIncludedBonusPoints", Integer.parseInt(amountOfIncluded));
        return "redirect:/profile/pay/" + reservation.getId();
    }
}
