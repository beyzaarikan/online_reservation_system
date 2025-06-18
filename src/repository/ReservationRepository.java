package repository;

import java.util.HashMap;
import models.Reservation;

public class ReservationRepository {

    public HashMap<String, Reservation> reservationMap;

    public ReservationRepository() {
        this.reservationMap = new HashMap<>();
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