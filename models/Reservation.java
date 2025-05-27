package models;

public class Reservation {
    private User user;
    private Trip trip;
    private Seat seat;

    public Reservation(User user, Trip trip, Seat seat) {
        this.user = user;
        this.trip = trip;
        this.seat = seat;
    }

    public boolean isReserved() {
        return seat.isReserved();
    }
}
