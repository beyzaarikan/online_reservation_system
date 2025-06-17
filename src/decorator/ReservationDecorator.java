package decorator;

import models.Reservation;

/**
 * Base decorator for adding features to reservations
 */
public abstract class ReservationDecorator {
    protected Reservation reservation;
    
    public ReservationDecorator(Reservation reservation) {
        this.reservation = reservation;
    }
    
    public abstract double calculateTotalPrice();
    public abstract String getDescription();
    
    // Delegate basic methods to the wrapped reservation
    public Reservation getReservation() {
        return reservation;
    }
}