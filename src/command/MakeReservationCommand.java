package command;
import models.*;
import service.*;

public class MakeReservationCommand implements Command {
    private ReservationService reservationService;
    private String userId;
    private String tripId;
    private String seatId;
    private String reservationId;
    
    public MakeReservationCommand(ReservationService reservationService, 
                                String userId, String tripId, String seatId) {
        this.reservationService = reservationService;
        this.userId = userId;
        this.tripId = tripId;
        this.seatId = seatId;
    }
    
    @Override
    public void execute() {
        try {
            reservationService.makeReservation(userId, tripId, seatId);
            System.out.println("Reservation made successfully");
        } catch (Exception e) {
            System.out.println("Failed to make reservation: " + e.getMessage());
        }
    }
    
    @Override
    public void undo() {
        if (reservationId != null) {
            reservationService.cancelReservation(reservationId);
            System.out.println("Reservation cancelled");
        }
    }
}