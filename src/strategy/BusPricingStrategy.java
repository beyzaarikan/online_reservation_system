package strategy;

import models.Trip;

public class BusPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Trip trip, int seatNumber) {
        double basePrice = trip.getBasePrice();
        double multiplier = 1.0;
        
        // Premium seats (first 10 seats) cost 30% more
        if (seatNumber <= 10) {
            multiplier += 0.3;
        }
        
        // Window seats cost 10% more
        if (isWindowSeat(seatNumber)) {
            multiplier += 0.1;
        }
        
        return basePrice * multiplier;
    }
    
    private boolean isWindowSeat(int seatNumber) {
        // Assuming bus layout: seats 1-2, 5-6, 9-10, etc. are window seats
        int seatInRow = ((seatNumber - 1) % 4) + 1;
        return seatInRow == 1 || seatInRow == 4;
    }
}
