package com.example.zavrsnirad.Models;

import javax.persistence.*;

@Table(name = "payment")
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Integer id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "is_included_bonus")
    private Boolean isIncludedBonus;

    @Column(name = "amount_of_included_bonus_points")
    private Integer amountOfIncludedBonusPoints;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Integer getAmountOfIncludedBonusPoints() {
        return amountOfIncludedBonusPoints;
    }

    public void setAmountOfIncludedBonusPoints(Integer amountOfIncludedBonusPoints) {
        this.amountOfIncludedBonusPoints = amountOfIncludedBonusPoints;
    }

    public Boolean getIsIncludedBonus() {
        return isIncludedBonus;
    }

    public void setIsIncludedBonus(Boolean isIncludedBonus) {
        this.isIncludedBonus = isIncludedBonus;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}