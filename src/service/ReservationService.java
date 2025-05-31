package service;

import models.Reservation;
import models.User;
import models.Trip;
import models.Seat;
import repository.ReservationRepository;
import repository.UserRepository;
import repository.TripRepository;
import java.util.Optional;
import java.util.List;
import java.util.UUID;
import java.util.Arrays;

public class ReservationService {
    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    private TripRepository tripRepository;
    
    public ReservationService(ReservationRepository reservationRepository, 
                            UserRepository userRepository, 
                            TripRepository tripRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
    }
    
    public boolean makeReservation(String userId, String tripId, String seatId) {
        // Check if user exists
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }
        
        // Check if trip exists
        Trip trip = tripRepository.findByTripNo(tripId);
        if (trip == null) {
            throw new IllegalArgumentException("Trip does not exist");
        }
        
        // Check if seat is available
        if (!isSeatAvailable(tripId, seatId)) {
            throw new IllegalArgumentException("Seat is not available");
        }
        
        // Find the specific seat from the trip
        Optional<Seat> seatOpt = trip.getSeats().stream()
                .filter(seat -> String.valueOf(seat.getSeatNo()).equals(seatId))
                .findFirst();
        
        if (!seatOpt.isPresent()) {
            throw new IllegalArgumentException("Seat does not exist on this trip");
        }
        
        // Create reservation
        String reservationId = UUID.randomUUID().toString();
        Seat seat = seatOpt.get();
        seat.reserve(); // Mark seat as reserved
        
        Reservation reservation = new Reservation(
            reservationId, 
            userOpt.get(), 
            trip, 
            Arrays.asList(seat)
        );
        
        reservationRepository.save(reservation);
        return true;
    }
    
    public boolean cancelReservation(String reservationId) {
        // Check if reservation exists
        if (!reservationRepository.reservationExists(reservationId)) {
            throw new IllegalArgumentException("Reservation does not exist");
        }
        
        // Get reservation to free up seats
        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation != null) {
            // Free up the seats by setting reserved to false
            reservation.getSeats().forEach(seat -> {
                seat.unreserve();
            });
        }
        
        // Cancel reservation
        return reservationRepository.deleteReservation(reservationId);
    }
    
    public boolean updateReservation(String reservationId, String newSeatId) {
        // Check if reservation exists
        if (!reservationRepository.reservationExists(reservationId)) {
            throw new IllegalArgumentException("Reservation does not exist");
        }
        
        Reservation reservation = reservationRepository.findById(reservationId);
        String tripId = reservation.getTrip().getTripNo();
        
        // Check if new seat is available
        if (!isSeatAvailable(tripId, newSeatId)) {
            throw new IllegalArgumentException("New seat is not available");
        }
        
        // Update reservation
        return reservationRepository.updateReservation(reservationId, newSeatId);
    }

    public boolean isTripExists(String tripId) {
        return tripRepository.existsByTripNo(tripId);
    }

    public boolean isSeatAvailable(String tripId, String seatId) {
        return reservationRepository.isSeatAvailable(tripId, seatId);
    }

    public void setReservationRepository(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationRepository getReservationRepository() {
        return reservationRepository;   
    }
    
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public void setTripRepository(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }
}