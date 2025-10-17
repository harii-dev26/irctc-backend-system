package com.hariish.irctc.model;

import java.time.LocalDateTime;

public class Ticket {
    private final String pnr;
    private final String trainId;
    private final String username;
    private final int seatsBooked;
    private final double amount;
    private final LocalDateTime bookingTime;

    public Ticket(String pnr, String trainId, String username, int seatsBooked, double amount) {
        this.pnr = pnr;
        this.trainId = trainId;
        this.username = username;
        this.seatsBooked = seatsBooked;
        this.amount = amount;
        this.bookingTime = LocalDateTime.now();
    }

    public String getPnr() { return pnr; }
    public String getTrainId() { return trainId; }
    public String getUsername() { return username; }
    public int getSeatsBooked() { return seatsBooked; }
    public double getAmount() { return amount; }
    public LocalDateTime getBookingTime() { return bookingTime; }

    @Override
    public String toString() {
        return String.format("PNR:%s | train:%s | user:%s | seats:%d | amt:%.2f | time:%s",
                pnr, trainId, username, seatsBooked, amount, bookingTime.toString());
    }
}
