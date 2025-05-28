package system;

import java.awt.List;
import java.util.ArrayList;
import models.Reservation;

public class ReservationSystem {
    private ArrayList<Reservation> reservations=new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation added: " + reservation);
    }

    public void removeReservation(Reservation reservation) {
        if(reservations.remove(reservation)) {
            System.out.println("Reservation removed: " + reservation);
        } else {
            System.out.println("Reservation not found: " + reservation);
        }
    }
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
}
