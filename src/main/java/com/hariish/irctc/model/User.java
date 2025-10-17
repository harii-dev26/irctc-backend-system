package com.hariish.irctc.model;

public class User {
    private final String username;
    private final String password; // plain in this demo; on real apps hash it
    private final String fullName;

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
}
