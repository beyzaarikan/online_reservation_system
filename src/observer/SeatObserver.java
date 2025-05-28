package observer;

public class SeatObserver implements Observer {
    private String seatNo;
    public SeatObserver(String seatNo) {
        this.seatNo = seatNo;
    }
    @Override
    public void update() {
        System.out.println("Seat " + seatNo + " has been updated.");
    }
}
