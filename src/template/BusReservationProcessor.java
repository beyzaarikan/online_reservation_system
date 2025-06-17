package template;

import models.Reservation;
import models.Trip;
import models.User;
import strategy.BusPricingStrategy;
import strategy.PricingContext;
import java.util.List;

/**
 * Concrete implementation for bus reservation processing
 */
public class BusReservationProcessor extends ReservationProcessor {
    
    @Override
    protected double calculatePrice(Trip trip, List<models.Seat> seats) {
        PricingContext pricingContext = new PricingContext(new BusPricingStrategy());
        double totalPrice = 0;
        
        for (models.Seat seat : seats) {
            totalPrice += pricingContext.calculatePrice(trip, seat.getSeatNo());
        }
        
        return totalPrice;
    }
    
    @Override
    protected boolean processPayment(User user, double amount) {
        // Simulate bus-specific payment processing
        System.out.println("Processing bus payment for user: " + user.getName());
        System.out.println("Amount: " + String.format("%.2f TL", amount));
        
        // Simulate payment gateway call
        try {
            Thread.sleep(1000); // Simulate processing time
            System.out.println("Bus payment processed successfully");
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }
    
    @Override
    protected void sendConfirmation(User user, Reservation reservation) {
        System.out.println("=== BUS RESERVATION CONFIRMATION ===");
        System.out.println("Dear " + user.getName() + ",");
        System.out.println("Your bus reservation has been confirmed!");
        System.out.println("Reservation ID: " + reservation.getId());
        System.out.println("Route: " + reservation.getTrip().getStartPoint() + 
                          " → " + reservation.getTrip().getEndPoint());
        System.out.println("Departure: " + reservation.getTrip().getDepartureTime());
        System.out.println("Seats: " + getSeatNumbers(reservation.getSeats()));
        System.out.println("Please arrive at the station 30 minutes before departure.");
        System.out.println("=====================================");
        
        // In a real application, this would send an email or SMS
    }
    
    private String getSeatNumbers(List<models.Seat> seats) {
        return seats.stream()
                   .map(seat -> String.valueOf(seat.getSeatNo()))
                   .reduce((s1, s2) -> s1 + ", " + s2)
                   .orElse("N/A");
    }
}