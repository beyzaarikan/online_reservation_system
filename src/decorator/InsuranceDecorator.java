package decorator;

import models.Reservation;

/**
 * Decorator that adds travel insurance to a reservation
 */
public class InsuranceDecorator extends ReservationDecorator {
    private static final double INSURANCE_RATE = 0.05; // 5% of base price
    
    public InsuranceDecorator(Reservation reservation) {
        super(reservation);
    }
    
    @Override
    public double calculateTotalPrice() {
        double basePrice = reservation.getTrip().getBasePrice() * reservation.getSeatCount();
        double insuranceCost = basePrice * INSURANCE_RATE;
        return basePrice + insuranceCost;
    }
    
    @Override
    public String getDescription() {
        return "Travel Insurance included (+5% of base price)";
    }
    
    public double getInsuranceCost() {
        double basePrice = reservation.getTrip().getBasePrice() * reservation.getSeatCount();
        return basePrice * INSURANCE_RATE;
    }
}