package state;

public class ReservationContext {
    private ReservationState currentState;
    private String reservationId;
    
    public ReservationContext(String reservationId) {
        this.reservationId = reservationId;
        this.currentState = new PendingState(this);
    }
    
    public void setState(ReservationState state) {
        this.currentState = state;
    }
    
    public void confirm() {
        currentState.confirm();
    }
    
    public void cancel() {
        currentState.cancel();
    }
    
    public void complete() {
        currentState.complete();
    }
    
    public String getCurrentStateName() {
        return currentState.getStateName();
    }
    
    public String getReservationId() {
        return reservationId;
    }
}