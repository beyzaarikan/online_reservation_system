package template;

import models.Reservation;
import models.User;
import models.Trip;
import java.util.List;

/**
 * Template Method pattern for reservation processing
 * Defines the skeleton of reservation algorithm
 */
public abstract class ReservationProcessor {
    
    /**
     * Template method that defines the reservation process steps
     */
    public final boolean processReservation(User user, Trip trip, List<models.Seat> seats) {
        try {
            // Step 1: Validate user
            if (!validateUser(user)) {
                System.out.println("User validation failed");
                return false;
            }
            
            // Step 2: Validate trip
            if (!validateTrip(trip)) {
                System.out.println("Trip validation failed");
                return false;
            }
            
            // Step 3: Validate seats
            if (!validateSeats(seats, trip)) {
                System.out.println("Seat validation failed");
                return false;
            }
            
            // Step 4: Calculate price (specific to each type)
            double totalPrice = calculatePrice(trip, seats);
            
            // Step 5: Process payment (specific to each type)
            if (!processPayment(user, totalPrice)) {
                System.out.println("Payment processing failed");
                return false;
            }
            
            // Step 6: Create reservation
            Reservation reservation = createReservation(user, trip, seats);
            
            // Step 7: Send confirmation (specific to each type)
            sendConfirmation(user, reservation);
            
            // Step 8: Update seat status
            updateSeatStatus(seats);
            
            System.out.println("Reservation processed successfully");
            return true;
            
        } catch (Exception e) {
            System.err.println("Error processing reservation: " + e.getMessage());
            return false;
        }
    }
    
    // Common validation methods (same for all types)
    protected boolean validateUser(User user) {
        return user != null && user.getId() != null && !user.getId().trim().isEmpty();
    }
    
    protected boolean validateTrip(Trip trip) {
        return trip != null && trip.getTripNo() != null && !trip.getTripNo().trim().isEmpty();
    }
    
    protected boolean validateSeats(List<models.Seat> seats, Trip trip) {
        if (seats == null || seats.isEmpty()) {
            return false;
        }
        
        // Check if all seats are available
        for (models.Seat seat : seats) {
            if (seat.isReserved()) {
                return false;
            }
        }
        
        return true;
    }
    
    protected Reservation createReservation(User user, Trip trip, List<models.Seat> seats) {
        String reservationId = java.util.UUID.randomUUID().toString();
        return new Reservation(reservationId, user, trip, seats);
    }
    
    protected void updateSeatStatus(List<models.Seat> seats) {
        for (models.Seat seat : seats) {
            seat.reserve();
        }
    }
    
    // Abstract methods that must be implemented by subclasses
    protected abstract double calculatePrice(Trip trip, List<models.Seat> seats);
    protected abstract boolean processPayment(User user, double amount);
    protected abstract void sendConfirmation(User user, Reservation reservation);
}