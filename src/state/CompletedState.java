package state;

public class CompletedState implements ReservationState {
    private ReservationContext context;
    
    public CompletedState(ReservationContext context) {
        this.context = context;
    }
    
    @Override
    public void confirm() {
        System.out.println("Reservation is already completed");
    }
    
    @Override
    public void cancel() {
        System.out.println("Cannot cancel a completed reservation");
    }
    
    @Override
    public void complete() {
        System.out.println("Reservation is already completed");
    }
    
    @Override
    public String getStateName() {
        return "COMPLETED";
    }
}
