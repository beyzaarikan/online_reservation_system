package command;
import models.*;
import service.*;

public class CancelReservationCommand implements Command {
    private ReservationService reservationService;
    private String reservationId;
    private Reservation backupReservation;
    
    public CancelReservationCommand(ReservationService reservationService, String reservationId) {
        this.reservationService = reservationService;
        this.reservationId = reservationId;
    }
    
    @Override
    public void execute() {
        // Backup reservation before cancelling
        backupReservation = reservationService.getReservationRepository().findById(reservationId);
        reservationService.cancelReservation(reservationId);
        System.out.println("Reservation cancelled");
    }
    
    @Override
    public void undo() {
        if (backupReservation != null) {
            reservationService.getReservationRepository().save(backupReservation);
            // Re-reserve seats
            backupReservation.getSeats().forEach(Seat::reserve);
            System.out.println("Reservation restored");
        }
    }
}