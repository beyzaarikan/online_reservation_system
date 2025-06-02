package state;

public class ConfirmedState implements ReservationState {
    private ReservationContext context;
    
    public ConfirmedState(ReservationContext context) {
        this.context = context;
    }
    
    @Override
    public void confirm() {
        System.out.println("Reservation is already confirmed");
    }
    
    @Override
    public void cancel() {
        System.out.println("Reservation cancelled from confirmed state");
        context.setState(new CancelledState(context));
    }
    
    @Override
    public void complete() {
        System.out.println("Reservation completed");
        context.setState(new CompletedState(context));
    }
    
    @Override
    public String getStateName() {
        return "CONFIRMED";
    }
}