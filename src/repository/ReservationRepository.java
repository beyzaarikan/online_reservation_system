package repository;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import models.Reservation;
import models.Seat;

public class ReservationRepository {

    HashMap<String, Reservation> reservationMap;

    public ReservationRepository() {
        this.reservationMap = new HashMap<>();
    }
    
    public Reservation createReservation(String id, Reservation reservation) {
        if (reservationMap.containsKey(id)) {
            throw new IllegalArgumentException("Reservation with id " + id + " already exists.");
        }
        reservationMap.put(id, reservation);
        return reservation;
    }

    public void save(Reservation reservation) {
        reservationMap.put(reservation.getId(), reservation);
    }

    public Reservation findById(String id) {
        return reservationMap.get(id);
    }
    
    public boolean existsById(String id) {
        return reservationMap.containsKey(id);
    }
    
    public void deleteById(String id) {
        reservationMap.remove(id);
    }
    
    public void update(Reservation reservation) {
        if (existsById(reservation.getId())) {
            reservationMap.put(reservation.getId(), reservation);
        } else {
            throw new IllegalArgumentException("Reservation with id " + reservation.getId() + " does not exist.");
        }
    }
    
    // Additional methods needed by ReservationService
    public boolean reservationExists(String reservationId) {
        return reservationMap.containsKey(reservationId);
    }
    
    public boolean deleteReservation(String reservationId) {
        if (reservationExists(reservationId)) {
            reservationMap.remove(reservationId);
            return true;
        }
        return false;
    }
    
    public boolean updateReservation(String reservationId, String newSeatId) {
        Reservation reservation = reservationMap.get(reservationId);
        if (reservation != null) {
            // Logic to update seat would depend on your specific requirements
            // This is a simplified implementation
            reservationMap.put(reservationId, reservation);
            return true;
        }
        return false;
    }
    
    public boolean isSeatAvailable(String tripId, String seatId) {
        // Check if any reservation already uses this seat for this trip
        return reservationMap.values().stream()
                .noneMatch(reservation -> 
                    reservation.getTrip().getTripNo().equals(tripId) &&
                    reservation.getSeats().stream()
                        .anyMatch(seat -> String.valueOf(seat.getSeatNo()).equals(seatId))
                );
    }
}