package models;

import java.util.List;

public class Reservation {
    private String id; 
    private User user;
    private Trip trip;
    private List<Seat> seats;

    public Reservation(String id,User user, Trip trip, List<Seat> seats) {
        this.id = id;
        this.user = user;
        this.trip = trip;
        this.seats = seats;
    }
    public String getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    
    public Trip getTrip() {
        return trip;
    }

    public int getSeatCount() {
        return seats.size(); 
    }
    public List<Seat> getSeats() { 
        return seats;
    }

}
