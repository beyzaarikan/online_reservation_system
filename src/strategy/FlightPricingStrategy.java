package strategy;

import models.Trip;

public class FlightPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Trip trip, int seatNumber) {
        double basePrice = trip.getBasePrice();
        double multiplier = 1.0;
        
        // First class seats 
        if (seatNumber <= 20 ) {
            multiplier += 2.0;
        }
        // Business class seats
        else if (seatNumber <= 60 && seatNumber > 20) {
            multiplier += 1.0;
        }
        // Premium economy seats 
        else if (seatNumber <= 100 && seatNumber > 60) {
            multiplier += 0.5;
        }
        return basePrice * multiplier;
    }
}