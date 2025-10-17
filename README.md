# IRCTC Backend System (Console Demo)

A **Core Java** backend simulation of a train reservation system.  
Designed as a recruiter-friendly project demonstrating OOP, Java Collections, and modular design — suitable for a resume/portfolio for SDE-1.

## Features
- User login and registration (in-memory users)
- Train search by source, destination, and date
- Book tickets with seat availability check
- Cancel booking (restores seats)
- Simple PNR generation and viewable bookings
- Demonstrates `ArrayList`, `HashMap`, and synchronized operations

## Run locally
Build & run (Maven required):

```bash
mvn clean compile exec:java


=== IRCTC Backend System (Console Demo) ===
1) Login  2) Register  3) Exit
> 1
username: hari
password: password123
Welcome, Hariish S (hari)

--- MENU (user: hari) ---
1) List all trains
2) Search trains by source,destination,date(YYYY-MM-DD)
3) Book train
4) View my bookings
5) Cancel booking (by PNR)
6) Logout
7) Exit
> 1
Available trains:
  T1001 | Coastal Express | Chennai -> Bengaluru | 2025-10-18 | seats: 120 | fare: 450.00
...

![Console Output](screenshots/irctc-demo.png)

##  Project Structure

irctc-backend-system/
 ├── pom.xml
 ├── README.md
 └── src/
     └── main/
         └── java/
             └── com/
                 └── hariish/
                     └── irctc/
                         ├── model/
                         ├── service/
                         └── util/


##  Tech Stack 


| Layer         | Technology                              |
| :------------ | :-------------------------------------- |
| Language      | Java 11                                 |
| Build Tool    | Maven (exec-maven-plugin)               |
| Core Concepts | OOP, Collections Framework, Console I/O |
| Paradigm      | Modular, Object-Oriented                |


## Future Enhancements

- Integrate JDBC or MySQL for persistence
- Add admin management for train schedule
- Support date validation and seat limits


## Author 
Hariish Srinivasan
Built as part of recruiter-ready Core Java projects for SDE-1 preparation.
Demonstrates clean coding practices, modularity, and real-world console application design.


