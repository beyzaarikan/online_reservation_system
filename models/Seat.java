package models;

public class Seat {
    private int seatNo;
    private boolean reserved;

    public Seat(int seatNo) {
        this.seatNo = seatNo;
        this.reserved = false;
    }
    public boolean isReserved() {
        return reserved;
    }
    public int getSeatNo() {
        return seatNo;
    }

    public void reserve() { //burasi degisebilir 
        if (!reserved) {
            reserved = true;
            System.out.println("Seat " + seatNo + " reserved.");
        } else {
            System.out.println("Seat " + seatNo + " already reserved.");
        }
    }

}
