package command;

import models.Reservation;
import system.ReservationSystem;

public class ReservedSeatCommand implements Command {
    private Reservation reservation;
    private ReservationSystem reservationSystem;

    public ReservedSeatCommand(Reservation reservation) {
        this.reservation = reservation;
    }
    @Override
    public void execute() {
        reservationSystem.addReservation(reservation);
    }
}