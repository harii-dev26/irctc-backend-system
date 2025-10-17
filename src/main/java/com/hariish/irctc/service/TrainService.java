package com.hariish.irctc.service;

import com.hariish.irctc.model.Train;
import com.hariish.irctc.util.DataStore;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TrainService {
    private final DataStore store;

    public TrainService(DataStore store) {
        this.store = store;
    }

    public List<Train> searchTrains(String source, String destination, LocalDate date) {
        return store.getAllTrains().stream()
                .filter(t -> t.getSource().equalsIgnoreCase(source.trim()))
                .filter(t -> t.getDestination().equalsIgnoreCase(destination.trim()))
                .filter(t -> t.getDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<Train> listAll() {
        return store.getAllTrains().stream().collect(Collectors.toList());
    }

    public Optional<Train> getById(String id) {
        return store.getTrainById(id);
    }
}
