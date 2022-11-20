package com.example.zavrsnirad.Controllers;

import com.example.zavrsnirad.Models.*;
import com.example.zavrsnirad.Models.frontendClasses.Rating;
import com.example.zavrsnirad.Models.frontendClasses.ReservationComplete;
import com.example.zavrsnirad.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/profile")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ScreeningService screeningService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserMovieService userMovieService;
    @Autowired
    private ScreeningSeatService screeningSeatService;


    @GetMapping(value = "")
    public String getUserPage(Model model, Authentication auth) throws Exception {

        if(auth != null) {
            Integer newReservationId = (Integer) model.asMap().get("newReservationId");
            UserModel user = userService.findUserByEmail(auth.getName());
            List<Reservation> reservationList = reservationService.findReservationByUserId(user.getId());
            List<ReservationComplete> activeReservations = new ArrayList<>();
            List<ReservationComplete> previousReservations = new ArrayList<>();
            ReservationComplete newReservation = null;
            for(Reservation reservation : reservationList) {
                ReservationComplete reservationComplete = new ReservationComplete();
                reservationComplete.setReservationId(reservation.getId());
                Screening screening =screeningService.findScreening(reservation.getScreening().getId());
                reservationComplete.setScreening(screening);
                reservationComplete.setMovie(movieService.findMovie(screening.getMovie().getId()));
                int numOfSeats = screeningSeatService.countByReservationId(reservation.getId());
                reservationComplete.setNumOfSeats(numOfSeats);
                try {
                    LocalDate date = screeningService.findScreening(reservation.getScreening().getId()).getDateOfScreening();
                    LocalTime time = screeningService.findScreening(reservation.getScreening().getId()).getEndScreeningTime();
                    LocalDateTime dateTime = date.atTime(time);
                    if(LocalDateTime.now().isAfter(dateTime)) {
                        reservationComplete.setPaid(true);
                        if(userMovieService.checkIfExistByUserIdAndMovieId(user.getId(), reservationComplete.getMovie().getId())) {
                            int grade = userMovieService.findByUserIdAndMovieId(user.getId(), reservationComplete.getMovie().getId()).getGrade();

                            System.out.println(grade);
                            List<Integer> stars = new ArrayList<>();
                            for(int i = 0 ; i < grade ; i++) {
                                stars.add(1);
                            }
                            reservationComplete.setStars(stars);
                        } else {
                            reservationComplete.setStars(null);
                        }
                        previousReservations.add(reservationComplete);
                    } else {
                        reservationComplete.setStars(null);
                        if(paymentService.existsByReservationId(reservation.getId())) {
                            reservationComplete.setPaid(true);
                        } else {
                            reservationComplete.setPaid(false);
                        }
                        if(newReservationId != null && reservation.getId() == newReservationId) {
                            newReservation = reservationComplete;
                        } else {
                            activeReservations.add(reservationComplete);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(newReservation != null) {
                model.addAttribute("numOfActiveReservations", activeReservations.size() + 1);
            } else {
                model.addAttribute("numOfActiveReservations", activeReservations.size());

            }
            if(auth != null) {
                model.addAttribute("loggedIn", true);
            } else {
                model.addAttribute("loggedIn", false);
            }

            model.addAttribute("rating", new Rating());
            model.addAttribute("newReservation", newReservation);
            model.addAttribute("activeReservations", activeReservations);
            model.addAttribute("previousReservations", previousReservations);
            model.addAttribute("user", user);
            return "Profile.html";
        } else {
            return "redirect:/?NotLoggedIn=true";
        }
    }

    @PostMapping(value = "/ChargeAccount")
    public String chargeAccount(Model model, Authentication auth, @RequestParam("amount") Double amount) {
        if(auth == null){
            return "redirect:/loginRouter";
        }
        try {
            UserModel user = userService.findUserByEmail(auth.getName());

            user.setWalletMoneyState(user.getWalletMoneyState() + amount);
            userService.saveUser(user);
        } catch (Exception exc) {
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }

    @GetMapping("/delete/{id}")
    public String deleteReservation(Model model, @PathVariable("id") String id) {
        int reservationId = Integer.parseInt(id);
        Payment payment = paymentService.findByReservationId(reservationId);
        if(payment != null) {
            paymentService.delete(payment);
        }
        screeningSeatService.unReserve(reservationId);
        reservationService.deleteByReservationId(reservationId);
        return "redirect:/profile";
    }

    @GetMapping("/pay/{id}/")
    public String payReservation(Model model, @PathVariable("id") String id, Authentication auth,
                                 @RequestParam(name = "amount") String amount,
                                 @RequestParam(name = "isIncluded") String isIncluded,
                                 @RequestParam(name = "amountOfIncluded") String amountOfIncluded) throws Exception {
        int reservationId = Integer.parseInt(id);
        Reservation reservation = reservationService.findById(reservationId);
        Payment payment = new Payment();
        Double redirectedAmount = (Double) model.asMap().get("amountOfPayment");
        Boolean redirectedIsIncluded = (Boolean) model.asMap().get("isIncludedBonusPoints");
        Integer redirectedAmountsOfIncluded = (Integer) model.asMap().get("amountOfIncludedBonusPoints");
        if(redirectedAmount != null) {
            payment.setAmount(redirectedAmount);
            payment.setIsIncludedBonus(redirectedIsIncluded);
            payment.setAmountOfIncludedBonusPoints(redirectedAmountsOfIncluded);
            payment.setReservation(reservation);
            paymentService.save(payment);
            UserModel user = userService.findUserByEmail(auth.getName());
            user.setWalletMoneyState(user.getWalletMoneyState() - redirectedAmount);
            if(redirectedIsIncluded) {
                user.setObtainedBonusPoints(user.getObtainedBonusPoints() - redirectedAmountsOfIncluded);
            }
            userService.saveUser(user);
        } else {
            payment.setAmount(Double.parseDouble(amount));
            payment.setIsIncludedBonus(Boolean.valueOf(isIncluded));
            payment.setAmountOfIncludedBonusPoints(Integer.parseInt(amountOfIncluded));
            payment.setReservation(reservation);
            paymentService.save(payment);
            UserModel user = userService.findUserByEmail(auth.getName());
            user.setWalletMoneyState(user.getWalletMoneyState() - Double.parseDouble(amount));
            if(Boolean.valueOf(isIncluded)) {
                user.setObtainedBonusPoints(user.getObtainedBonusPoints() - Integer.parseInt(amountOfIncluded));
            }
            userService.saveUser(user);
        }

        return "redirect:/profile";
    }

    @GetMapping(value = "/grade/{id}/{grade}")
    public String grade(Model model, @PathVariable("id") String id, @PathVariable("grade") String grade , Authentication auth) throws Exception {
        int movieGrade = Integer.parseInt(grade);
        int movieId = Integer.parseInt(id);
        if(auth != null) {
            UserModel user = userService.findUserByEmail(auth.getName());
            UserMovie userMovie = new UserMovie();
            Movie movie = movieService.findMovie(movieId);
            userMovie.setGrade(movieGrade);
            userMovie.setUser(user);
            userMovie.setMovie(movie);
            userMovieService.save(userMovie);
        }

        return "redirect:/profile";
    }
}
