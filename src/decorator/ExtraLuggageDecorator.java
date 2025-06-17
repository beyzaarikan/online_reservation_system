package decorator;

import models.Reservation;

/**
 * Decorator that adds extra luggage allowance to a reservation
 */
public class ExtraLuggageDecorator extends ReservationDecorator {
    private static final double EXTRA_LUGGAGE_FEE = 50.0; // Per bag
    private int extraBags;
    
    public ExtraLuggageDecorator(Reservation reservation, int extraBags) {
        super(reservation);
        this.extraBags = extraBags;
    }
    
    @Override
    public double calculateTotalPrice() {
        double basePrice = reservation.getTrip().getBasePrice() * reservation.getSeatCount();
        return basePrice + (EXTRA_LUGGAGE_FEE * extraBags);
    }
    
    @Override
    public String getDescription() {
        return String.format("Extra Luggage (%d bag%s) (+%.2f TL)", 
                           extraBags, extraBags > 1 ? "s" : "", EXTRA_LUGGAGE_FEE * extraBags);
    }
    
    public double getExtraLuggageFee() {
        return EXTRA_LUGGAGE_FEE * extraBags;
    }
    
    public int getExtraBags() {
        return extraBags;
    }
}