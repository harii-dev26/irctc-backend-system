package com.hariish.irctc.model;

import java.time.LocalDate;

public class Train {
    private final String id;
    private final String name;
    private final String source;
    private final String destination;
    private final LocalDate date;
    private final double fare;
    private int totalSeats;
    private int availableSeats;

    public Train(String id, String name, String source, String destination, LocalDate date, int totalSeats, double fare) {
        this.id = id;
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.fare = fare;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public LocalDate getDate() { return date; }
    public double getFare() { return fare; }
    public int getTotalSeats() { return totalSeats; }
    public synchronized int getAvailableSeats() { return availableSeats; }

    public synchronized boolean bookSeats(int n) {
        if (n <= 0) return false;
        if (availableSeats >= n) {
            availableSeats -= n;
            return true;
        }
        return false;
    }

    public synchronized void cancelSeats(int n) {
        if (n <= 0) return;
        availableSeats += n;
        if (availableSeats > totalSeats) availableSeats = totalSeats;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s -> %s | %s | seats: %d | fare: %.2f",
                id, name, source, destination, date.toString(), availableSeats, fare);
    }
}
