package com.hariish.irctc.util;

import com.hariish.irctc.model.Train;
import com.hariish.irctc.model.User;

import java.time.LocalDate;
import java.util.*;

public class DataStore {
    private final Map<String, Train> trains = new LinkedHashMap<>();
    private final Map<String, User> users = new HashMap<>();

    public DataStore() {
        seedUsers();
        seedTrains();
    }

    private void seedUsers() {
        // demo users
        users.put("hari", new User("hari", "password123", "Hariish S"));
        users.put("alice", new User("alice", "alice123", "Alice"));
        users.put("bob", new User("bob", "bob123", "Bob"));
    }

    private void seedTrains() {
        addTrain(new Train("T1001", "Coastal Express", "Chennai", "Bengaluru", LocalDate.now().plusDays(1), 120, 450.0));
        addTrain(new Train("T1002", "Morning Star", "Mumbai", "Pune", LocalDate.now().plusDays(2), 80, 250.0));
        addTrain(new Train("T1003", "Night Rider", "Delhi", "Lucknow", LocalDate.now().plusDays(1), 100, 500.0));
        addTrain(new Train("T1004", "Riverfront", "Kolkata", "Bhagalpur", LocalDate.now().plusDays(3), 70, 300.0));
        addTrain(new Train("T1005", "Western Fast", "Ahmedabad", "Surat", LocalDate.now().plusDays(1), 90, 200.0));
    }

    public void addTrain(Train t) {
        trains.put(t.getId(), t);
    }

    public Collection<Train> getAllTrains() {
        return Collections.unmodifiableCollection(trains.values());
    }

    public Optional<Train> getTrainById(String id) {
        return Optional.ofNullable(trains.get(id));
    }

    public Optional<User> getUser(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public boolean registerUser(String username, String password, String fullName) {
        if (users.containsKey(username)) return false;
        users.put(username, new User(username, password, fullName));
        return true;
    }
}
