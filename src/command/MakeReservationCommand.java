package command;
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
    public void execute() { // Attempt to make a reservation
        try {
            boolean success = reservationService.makeReservation(userId, tripId, seatId);
            if (success) {
                System.out.println("Reservation made successfully for user: " + userId);
            }
        } catch (Exception e) {
            System.out.println("Failed to make reservation: " + e.getMessage());
        }
    }
    
    @Override
    public void undo() { // Attempt to cancel the reservation
        if (reservationId != null) {
            try {
                reservationService.cancelReservation(reservationId);
                System.out.println("Reservation cancelled: " + reservationId);
            } catch (Exception e) {
                System.out.println("Failed to cancel reservation: " + e.getMessage());
            }
        }
    }
    
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }
}