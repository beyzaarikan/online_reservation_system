package state;

public class PendingState implements ReservationState {
    private ReservationContext context;
    
    public PendingState(ReservationContext context) {
        this.context = context;
    }
    
    @Override
    public void confirm() {
        System.out.println("Reservation confirmed");
        context.setState(new ConfirmedState(context));
    }
    
    @Override
    public void cancel() {
        System.out.println("Reservation cancelled from pending state");
        context.setState(new CancelledState(context));
    }
    
    @Override
    public void complete() {
        System.out.println("Cannot complete reservation from pending state");
    }
    
    @Override
    public String getStateName() {
        return "PENDING";
    }
}