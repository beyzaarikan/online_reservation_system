package strategy;

import models.Trip;

public class FlightPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Trip trip, int seatNumber) {
        double basePrice = trip.getBasePrice();
        double multiplier = 1.0;
        
        // First class seats (first 20 seats) cost 200% more
        if (seatNumber <= 20) {
            multiplier += 2.0;
        }
        // Business class seats (seats 21-60) cost 100% more
        else if (seatNumber <= 60) {
            multiplier += 1.0;
        }
        // Premium economy seats (seats 61-100) cost 50% more
        else if (seatNumber <= 100) {
            multiplier += 0.5;
        }
        
        // Window seats cost 15% more
        if (isWindowSeat(seatNumber)) {
            multiplier += 0.15;
        }
        
        return basePrice * multiplier;
    }
    
    private boolean isWindowSeat(int seatNumber) {
        // Assuming flight layout: seats A and F are window seats in each row
        int seatInRow = ((seatNumber - 1) % 6) + 1;
        return seatInRow == 1 || seatInRow == 6;
    }
}