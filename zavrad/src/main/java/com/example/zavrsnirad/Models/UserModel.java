package com.example.zavrsnirad.Models;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "user_table")
@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "firstname", nullable = false, length = 30)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 30)
    private String lastname;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Column(name = "is_member", nullable = false)
    private Boolean isMember = false;

    @Column(name = "iban", length = 15, nullable = true)
    private String iban;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "adress", nullable = true, length = 100)
    private String adress;

    @Column(name = "obtained_bonus_points", nullable = true)
    private Integer obtainedBonusPoints;

    @Column(name = "wallet_money_state", nullable = true)
    private Double walletMoneyState;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "type", nullable = false, length = 10)
    private String type;

    @Column(name = "confirmed", nullable = false)
    private Boolean confirmed = false;

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Double getWalletMoneyState() {
        return walletMoneyState;
    }

    public void setWalletMoneyState(Double walletMoneyState) {
        this.walletMoneyState = walletMoneyState;
    }

    public Integer getObtainedBonusPoints() {
        return obtainedBonusPoints;
    }

    public void setObtainedBonusPoints(Integer obtainedBonusPoints) {
        this.obtainedBonusPoints = obtainedBonusPoints;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Boolean getIsMember() {
        return isMember;
    }

    public void setIsMember(Boolean isMember) {
        this.isMember = isMember;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAdmin(){
        return type.equals("admin");
    }
}