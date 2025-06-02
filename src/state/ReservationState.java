package state;

public interface ReservationState {
    void confirm();
    void cancel();
    void complete();
    String getStateName();
}
