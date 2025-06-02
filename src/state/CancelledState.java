package state;

public class CancelledState implements ReservationState {
    private ReservationContext context;
    
    public CancelledState(ReservationContext context) {
        this.context = context;
    }
    
    @Override
    public void confirm() {
        System.out.println("Cannot confirm a cancelled reservation");
    }
    
    @Override
    public void cancel() {
        System.out.println("Reservation is already cancelled");
    }
    
    @Override
    public void complete() {
        System.out.println("Cannot complete a cancelled reservation");
    }
    
    @Override
    public String getStateName() {
        return "CANCELLED";
    }
}