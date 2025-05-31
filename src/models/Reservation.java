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
    public void setUser(User user) {
        this.user = user;
    }
    public void setTrip(Trip trip) {
        this.trip = trip;
    }
    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getSeatCount() {
        return seats.size(); // burada seatCount doğrudan hesaplanır
    }

    public boolean isReserved() {
        return seats.stream().allMatch(Seat::isReserved);
    }

    public List<Seat> getSeats() { //gerekliligi tartiusilir
        return seats;
    }

}
