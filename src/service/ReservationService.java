package service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import models.BusTrip;
import models.Reservation;
import models.Seat;
import models.Trip;
import models.User;
import repository.ReservationRepository;
import repository.TripRepository;
import repository.UserRepository;
import singleton.SeatStatusManager; // Import SeatStatusManager

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
            // Get the SeatStatusManager instance
            SeatStatusManager seatStatusManager = SeatStatusManager.getInstance();
            Trip trip = reservation.getTrip();
            String tripKey;

            if (reservation.getTrip() instanceof models.BusTrip) {
                tripKey = ((BusTrip) trip).getCompany();
            } else if (reservation.getTrip() instanceof models.FlightTrip) {
                tripKey = trip.getTripNo();
            } else {
                tripKey = trip.getTripNo();
            }

            // Unmark each seat as occupied in SeatStatusManager
            for (Seat seat : reservation.getSeats()) {
                seat.unreserve(); // Mark seat as unreserved in the Seat object
                // Remove seat from SeatStatusManager's occupied list
                seatStatusManager.getOccupiedSeats(tripKey).remove(Integer.valueOf(seat.getSeatNo()));
            }
        }
        
        return reservationRepository.deleteReservation(reservationId);
    }
    
    public boolean isSeatAvailable(String tripId, String seatId) {
        return reservationRepository.isSeatAvailable(tripId, seatId);
    }

    public ReservationRepository getReservationRepository() {
        return reservationRepository;   
    }

}