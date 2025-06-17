package template;

import models.Reservation;
import models.Trip;
import models.User;
import strategy.FlightPricingStrategy;
import strategy.PricingContext;
import java.util.List;

/**
 * Concrete implementation for flight reservation processing
 */
public class FlightReservationProcessor extends ReservationProcessor {
    
    @Override
    protected double calculatePrice(Trip trip, List<models.Seat> seats) {
        PricingContext pricingContext = new PricingContext(new FlightPricingStrategy());
        double totalPrice = 0;
        
        for (models.Seat seat : seats) {
            totalPrice += pricingContext.calculatePrice(trip, seat.getSeatNo());
        }
        
        return totalPrice;
    }
    
    @Override
    protected boolean processPayment(User user, double amount) {
        // Simulate flight-specific payment processing
        System.out.println("Processing flight payment for user: " + user.getName());
        System.out.println("Amount: " + String.format("$%.2f", amount));
        
        // Flight payments might require additional verification
        if (amount > 1000) {
            System.out.println("High-value transaction - additional verification required");
            // Simulate additional verification
        }
        
        try {
            Thread.sleep(1500); // Simulate longer processing time for flights
            System.out.println("Flight payment processed successfully");
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }
    
    @Override
    protected void sendConfirmation(User user, Reservation reservation) {
        System.out.println("=== FLIGHT RESERVATION CONFIRMATION ===");
        System.out.println("Dear " + user.getName() + ",");
        System.out.println("Your flight reservation has been confirmed!");
        System.out.println("Reservation ID: " + reservation.getId());
        System.out.println("Flight: " + reservation.getTrip().getStartPoint() + 
                          " → " + reservation.getTrip().getEndPoint());
        System.out.println("Departure: " + reservation.getTrip().getDepartureTime());
        System.out.println("Seats: " + getSeatNumbers(reservation.getSeats()));
        System.out.println("Please check-in online 24 hours before departure.");
        System.out.println("Arrive at the airport 2 hours before international flights.");
        System.out.println("========================================");
        
        // In a real application, this would send an email with boarding pass
    }
    
    private String getSeatNumbers(List<models.Seat> seats) {
        return seats.stream()
                   .map(seat -> String.valueOf(seat.getSeatNo()))
                   .reduce((s1, s2) -> s1 + ", " + s2)
                   .orElse("N/A");
    }
}