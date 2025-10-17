package com.hariish.irctc;

import com.hariish.irctc.model.Ticket;
import com.hariish.irctc.model.Train;
import com.hariish.irctc.util.DataStore;
import com.hariish.irctc.service.BookingService;
import com.hariish.irctc.service.TrainService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static String currentUser = null;

    public static void main(String[] args) {
        DataStore ds = new DataStore();
        TrainService ts = new TrainService(ds);
        BookingService bs = new BookingService(ts);

        System.out.println("=== IRCTC Backend System (Console Demo) ===");
        boolean running = true;
        while (running) {
            if (currentUser == null) {
                System.out.println("\n1) Login  2) Register  3) Exit");
                System.out.print("> ");
                String option = scanner.nextLine().trim();
                switch (option) {
                    case "1": login(ds); break;
                    case "2": register(ds); break;
                    case "3": running = false; break;
                    default: System.out.println("Invalid option");
                }
            } else {
                showUserMenu();
                String opt = scanner.nextLine().trim();
                switch (opt) {
                    case "1": listAllTrains(ts); break;
                    case "2": searchTrains(ts); break;
                    case "3": bookTrain(ts, bs); break;
                    case "4": viewBookings(bs); break;
                    case "5": cancelBooking(bs); break;
                    case "6": logout(); break;
                    case "7": running = false; break;
                    default: System.out.println("Unknown option");
                }
            }
        }
        System.out.println("Exiting. Goodbye.");
    }

    private static void login(DataStore ds) {
        System.out.print("username: ");
        String u = scanner.nextLine().trim();
        System.out.print("password: ");
        String p = scanner.nextLine().trim();
        Optional<com.hariish.irctc.model.User> maybe = ds.getUser(u);
        if (maybe.isEmpty()) {
            System.out.println("No such user. Register first.");
            return;
        }
        if (!maybe.get().getPassword().equals(p)) {
            System.out.println("Invalid credentials.");
            return;
        }
        currentUser = u;
        System.out.println("Welcome, " + maybe.get().getFullName() + " (" + currentUser + ")");
    }

    private static void register(DataStore ds) {
        System.out.print("Choose username: ");
        String u = scanner.nextLine().trim();
        System.out.print("Choose password: ");
        String p = scanner.nextLine().trim();
        System.out.print("Full name: ");
        String name = scanner.nextLine().trim();
        boolean ok = ds.registerUser(u, p, name);
        if (ok) System.out.println("Registered. Please login.");
        else System.out.println("User exists. Try login.");
    }

    private static void logout() {
        System.out.println("Logging out " + currentUser);
        currentUser = null;
    }

    private static void showUserMenu() {
        System.out.println("\n--- MENU (user: " + currentUser + ") ---");
        System.out.println("1) List all trains");
        System.out.println("2) Search trains by source,destination,date(YYYY-MM-DD)");
        System.out.println("3) Book train");
        System.out.println("4) View my bookings");
        System.out.println("5) Cancel booking (by PNR)");
        System.out.println("6) Logout");
        System.out.println("7) Exit");
        System.out.print("> ");
    }

    private static void listAllTrains(TrainService ts) {
        List<Train> all = ts.listAll();
        System.out.println("Available trains:");
        all.forEach(t -> System.out.println("  " + t.toString()));
    }

    private static void searchTrains(TrainService ts) {
        System.out.print("Source: ");
        String s = scanner.nextLine().trim();
        System.out.print("Destination: ");
        String d = scanner.nextLine().trim();
        System.out.print("Date (YYYY-MM-DD): ");
        String dateS = scanner.nextLine().trim();
        try {
            LocalDate date = LocalDate.parse(dateS);
            List<Train> found = ts.searchTrains(s, d, date);
            if (found.isEmpty()) System.out.println("No trains found.");
            else found.forEach(t -> System.out.println("  " + t.toString()));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format.");
        }
    }

    private static void bookTrain(TrainService ts, BookingService bs) {
        System.out.print("Enter train id: ");
        String id = scanner.nextLine().trim();
        Optional<Train> maybe = ts.getById(id);
        if (maybe.isEmpty()) { System.out.println("Train not found"); return; }
        Train t = maybe.get();
        System.out.println("Selected: " + t.toString());
        System.out.print("Seats to book: ");
        try {
            int seats = Integer.parseInt(scanner.nextLine().trim());
            Optional<Ticket> maybeTicket = bs.book(currentUser, id, seats);
            if (maybeTicket.isPresent()) {
                System.out.println("Booking successful!");
                System.out.println(maybeTicket.get().toString());
            } else {
                System.out.println("Booking failed (not enough seats or error).");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number");
        }
    }

    private static void viewBookings(BookingService bs) {
        List<Ticket> my = bs.getTicketsForUser(currentUser);
        if (my.isEmpty()) System.out.println("No bookings found.");
        else my.forEach(t -> System.out.println("  " + t.toString()));
    }

    private static void cancelBooking(BookingService bs) {
        System.out.print("Enter PNR to cancel: ");
        String pnr = scanner.nextLine().trim();
        boolean ok = bs.cancel(currentUser, pnr);
        if (ok) System.out.println("Cancelled " + pnr);
        else System.out.println("Cancel failed (PNR not found or not yours).");
    }
}
