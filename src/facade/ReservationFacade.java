package facade;

import command.CommandInvoker;
import command.MakeReservationCommand;
import command.CancelReservationCommand;
import models.*;
import repository.*;
import service.*;
import singleton.SessionManager;
import state.ReservationContext;
import template.BusReservationProcessor;
import template.FlightReservationProcessor;
import template.ReservationProcessor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Facade pattern implementation that simplifies the complex reservation system
 * Provides a unified interface for all reservation operations
 */
public class ReservationFacade {
    // All the complex subsystems
    private TripRepository tripRepository;
    private UserRepository userRepository;
    private ReservationRepository reservationRepository;
    private TripService tripService;
    private ReservationService reservationService;
    private UserService userService;
    private CommandInvoker commandInvoker;
    
    // Template method processors
    private BusReservationProcessor busProcessor;
    private FlightReservationProcessor flightProcessor;
    
    public ReservationFacade() {
        // Initialize all subsystems
        this.tripRepository = TripRepository.getInstance();
        this.userRepository = UserRepository.getInstance();
        this.reservationRepository = new ReservationRepository();
        this.tripService = new TripService(tripRepository);
        this.reservationService = new ReservationService(reservationRepository, userRepository, tripRepository);
        this.userService = new UserService(userRepository);
        this.commandInvoker = new CommandInvoker();
        
        // Initialize processors
        this.busProcessor = new BusReservationProcessor();
        this.flightProcessor = new FlightReservationProcessor();
    }
    
    /**
     * Simple method to search for trips
     */
    public List<Trip> searchTrips(String from, String to, LocalDateTime date) {
        return tripService.searchTrips(from, to, date);
    }
    
    /**
     * Simple method to get available seats for a trip
     */
    public List<Seat> getAvailableSeats(String tripNo) {
        return tripService.findAvailableSeats(tripNo);
    }
    
    /**
     * Simplified reservation creation - handles all complexity internally
     */
    public ReservationResult createReservation(String tripNo, List<Integer> seatNumbers) {
        try {
            // Get current user
            User currentUser = SessionManager.getInstance().getLoggedInUser();
            if (currentUser == null) {
                return new ReservationResult(false, "User not logged in", null);
            }
            
            // Find trip
            Trip trip = tripRepository.findByTripNo(tripNo);
            if (trip == null) {
                return new ReservationResult(false, "Trip not found", null);
            }
            
            // Get seats
            List<Seat> selectedSeats = trip.getSeats().stream()
                .filter(seat -> seatNumbers.contains(seat.getSeatNo()))
                .collect(java.util.stream.Collectors.toList());
            
            if (selectedSeats.size() != seatNumbers.size()) {
                return new ReservationResult(false, "Some seats not found", null);
            }
            
            // Check if seats are available
            for (Seat seat : selectedSeats) {
                if (seat.isReserved()) {
                    return new ReservationResult(false, "Seat " + seat.getSeatNo() + " is already reserved", null);
                }
            }
            
            // Use appropriate processor based on trip type
            ReservationProcessor processor = trip instanceof BusTrip ? busProcessor : flightProcessor;
            
            // Process reservation using template method
            boolean success = processor.processReservation(currentUser, trip, selectedSeats);
            
            if (success) {
                // Create reservation object
                String reservationId = java.util.UUID.randomUUID().toString();
                Reservation reservation = new Reservation(reservationId, currentUser, trip, selectedSeats);
                
                // Save reservation
                reservationRepository.save(reservation);
                
                // Set reservation state to confirmed
                ReservationContext context = new ReservationContext(reservationId);
                context.confirm();
                
                return new ReservationResult(true, "Reservation created successfully", reservation);
            } else {
                return new ReservationResult(false, "Failed to process reservation", null);
            }
            
        } catch (Exception e) {
            return new ReservationResult(false, "Error: " + e.getMessage(), null);
        }
    }
    
    /**
     * Simplified reservation cancellation
     */
    public ReservationResult cancelReservation(String reservationId) {
        try {
            // Check if reservation exists
            Reservation reservation = reservationRepository.findById(reservationId);
            if (reservation == null) {
                return new ReservationResult(false, "Reservation not found", null);
            }
            
            // Check if user owns this reservation
            User currentUser = SessionManager.getInstance().getLoggedInUser();
            if (currentUser == null || !reservation.getUser().getId().equals(currentUser.getId())) {
                return new ReservationResult(false, "Unauthorized to cancel this reservation", null);
            }
            
            // Use command pattern for cancellation (with undo capability)
            CancelReservationCommand cancelCommand = new CancelReservationCommand(reservationService, reservationId);
            commandInvoker.executeCommand(cancelCommand);
            
            return new ReservationResult(true, "Reservation cancelled successfully", reservation);
            
        } catch (Exception e) {
            return new ReservationResult(false, "Error: " + e.getMessage(), null);
        }
    }
    
    /**
     * Get user's reservations
     */
    public List<Reservation> getUserReservations() {
        User currentUser = SessionManager.getInstance().getLoggedInUser();
        if (currentUser == null) {
            return java.util.Collections.emptyList();
        }
        
        return reservationRepository.reservationMap.values().stream()
            .filter(r -> r.getUser().getId().equals(currentUser.getId()))
            .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Login user
     */
    public boolean loginUser(String email, String password) {
        Optional<User> userOpt = userService.login(email, password);
        if (userOpt.isPresent()) {
            SessionManager.getInstance().setLoggedInUser(userOpt.get());
            return true;
        }
        return false;
    }
    
    /**
     * Logout user
     */
    public void logoutUser() {
        SessionManager.getInstance().logout();
    }
    
    /**
     * Register new user
     */
    public boolean registerUser(String username, String password, String email, String fullName) {
        try {
            userService.registerCustomer(username, password, email, fullName, "");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Undo last operation (if supported)
     */
    public void undoLastOperation() {
        commandInvoker.undoLastCommand();
    }
    
    /**
     * Result class for reservation operations
     */
    public static class ReservationResult {
        private boolean success;
        private String message;
        private Reservation reservation;
        
        public ReservationResult(boolean success, String message, Reservation reservation) {
            this.success = success;
            this.message = message;
            this.reservation = reservation;
        }
        
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public Reservation getReservation() { return reservation; }
    }
}