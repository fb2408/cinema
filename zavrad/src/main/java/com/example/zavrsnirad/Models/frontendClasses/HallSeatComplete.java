package com.example.zavrsnirad.Models.frontendClasses;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class HallSeatComplete {
    private int rowNumber;
    private int columnNumber;
    private boolean isOccupied;
    private int cinemaHallId;
    private int screeningId;
    private String hallSeatCompleteId;

    public String getHallSeatCompleteId() {
        return hallSeatCompleteId;
    }

    public void setHallSeatCompleteId(String hallSeatCompleteId) {
        this.hallSeatCompleteId = hallSeatCompleteId;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public int getCinemaHallId() {
        return cinemaHallId;
    }

    public int getScreeningId() {
        return screeningId;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public void setCinemaHallId(int cinemaHallId) {
        this.cinemaHallId = cinemaHallId;
    }

    public void setScreeningId(int screeningId) {
        this.screeningId = screeningId;
    }
}
