package decorator;

import models.Reservation;

/**
 * Decorator that adds priority boarding to a reservation
 */
public class PriorityBoardingDecorator extends ReservationDecorator {
    private static final double PRIORITY_BOARDING_FEE = 25.0; // Fixed fee
    
    public PriorityBoardingDecorator(Reservation reservation) {
        super(reservation);
    }
    
    @Override
    public double calculateTotalPrice() {
        double basePrice = reservation.getTrip().getBasePrice() * reservation.getSeatCount();
        return basePrice + PRIORITY_BOARDING_FEE;
    }
    
    @Override
    public String getDescription() {
        return "Priority Boarding (+25.00 TL)";
    }
    
    public double getPriorityBoardingFee() {
        return PRIORITY_BOARDING_FEE;
    }
}