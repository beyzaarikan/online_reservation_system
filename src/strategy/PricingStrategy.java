package strategy;

import models.Trip;

public interface PricingStrategy {
    double calculatePrice(Trip trip, int seatNumber); 
} 