package strategy;

import models.Trip;

public class PricingContext {
    private PricingStrategy strategy;
    
    public PricingContext(PricingStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(PricingStrategy strategy) {
        this.strategy = strategy;
    }
    
    public double calculatePrice(Trip trip, int seatNumber) {
        return strategy.calculatePrice(trip, seatNumber);
    }
}