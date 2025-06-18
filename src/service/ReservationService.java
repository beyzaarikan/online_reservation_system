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
import singleton.SeatStatusManager; 

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
    
    // Make a reservation for a user on a specific trip and seat
    public boolean makeReservation(String userId, String tripId, String seatId) { 
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }
        
        Trip trip = tripRepository.findByTripNo(tripId);
        if (trip == null) {
            throw new IllegalArgumentException("Trip does not exist");
        }
        
        if (!isSeatAvailable(tripId, seatId)) {
            throw new IllegalArgumentException("Seat is not available");
        }
        
        Optional<Seat> seatOpt = trip.getSeats().stream() 
                .filter(seat -> String.valueOf(seat.getSeatNo()).equals(seatId))
                .findFirst(); // Find the seat by its ID
        
        if (!seatOpt.isPresent()) {
            throw new IllegalArgumentException("Seat does not exist on this trip");
        }
        
        // Create reservation
        String reservationId = UUID.randomUUID().toString();
        Seat seat = seatOpt.get();
        seat.reserve(); 
        
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
        if (!reservationRepository.reservationExists(reservationId)) {
            throw new IllegalArgumentException("Reservation does not exist");
        }
        
        // Get reservation to free up seats
        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation != null) {
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

            for (Seat seat : reservation.getSeats()) {
                seat.unreserve(); 
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