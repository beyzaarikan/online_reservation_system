package command;

import models.Reservation;
import system.ReservationSystem;

public class CancelReservationCommand implements Command{
    private Reservation reservation;
    private ReservationSystem reservationSystem;

    public CancelReservationCommand(Reservation reservation) {
        this.reservation = reservation;
    }
    @Override
    public void execute() {
        reservationSystem.removeReservation(reservation);
    }
}
