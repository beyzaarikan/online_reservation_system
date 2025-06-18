package strategy;

import models.Trip;

public class BusPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Trip trip, int seatNumber) {
        double basePrice = trip.getBasePrice();
        double multiplier = 1.0;
        
        // Premium seats
        if (seatNumber ==1 || seatNumber == 2 || seatNumber == 11 || seatNumber == 20 || seatNumber == 21 || seatNumber == 22) {
            multiplier += 0.3;
        }
        return basePrice * multiplier;
    }
    
}

