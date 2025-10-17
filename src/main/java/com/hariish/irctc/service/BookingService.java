package com.hariish.irctc.service;

import com.hariish.irctc.model.Ticket;
import com.hariish.irctc.model.Train;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BookingService {
    // pnr -> ticket
    private final Map<String, Ticket> tickets = new ConcurrentHashMap<>();
    // user -> pnr list
    private final Map<String, List<String>> userBookings = new ConcurrentHashMap<>();

    private final TrainService trainService;

    public BookingService(TrainService trainService) {
        this.trainService = trainService;
    }

    public synchronized Optional<Ticket> book(String username, String trainId, int seats) {
        if (seats <= 0) return Optional.empty();
        Optional<Train> maybe = trainService.getById(trainId);
        if (maybe.isEmpty()) return Optional.empty();
        Train train = maybe.get();
        boolean ok = train.bookSeats(seats);
        if (!ok) return Optional.empty();
        double amount = seats * train.getFare();
        String pnr = generatePnr();
        Ticket t = new Ticket(pnr, trainId, username, seats, amount);
        tickets.put(pnr, t);
        userBookings.computeIfAbsent(username, k -> Collections.synchronizedList(new ArrayList<>())).add(pnr);
        return Optional.of(t);
    }

    public synchronized boolean cancel(String username, String pnr) {
        Ticket t = tickets.get(pnr);
        if (t == null) return false;
        if (!t.getUsername().equals(username)) return false;
        // restore seats
        Optional<Train> maybe = trainService.getById(t.getTrainId());
        maybe.ifPresent(train -> train.cancelSeats(t.getSeatsBooked()));
        tickets.remove(pnr);
        List<String> list = userBookings.getOrDefault(username, new ArrayList<>());
        list.remove(pnr);
        return true;
    }

    public List<Ticket> getTicketsForUser(String username) {
        List<String> list = userBookings.getOrDefault(username, Collections.emptyList());
        List<Ticket> out = new ArrayList<>();
        for (String p : list) {
            Ticket t = tickets.get(p);
            if (t != null) out.add(t);
        }
        return out;
    }

    private String generatePnr() {
        return "PNR" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
