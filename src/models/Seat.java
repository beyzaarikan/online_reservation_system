package models;

import java.util.ArrayList;
import java.util.List;

public class Seat {
    private int seatNo;
    private boolean reserved;

    public Seat(int seatNo, boolean reserved) {
        this.seatNo = seatNo;
        this.reserved = reserved;
    }
    
    public boolean isReserved() {
        return reserved;
    }
    
    public int getSeatNo() {
        return seatNo;
    }

    public void reserve() {
        if (!reserved) {
            reserved = true;
            System.out.println("Seat " + seatNo + " reserved.");
        } else {
            System.out.println("Seat " + seatNo + " already reserved.");
        }
    }
    
    public void unreserve() {
        if (reserved) {
            reserved = false;
            System.out.println("Seat " + seatNo + " unreserved.");
        } else {
            System.out.println("Seat " + seatNo + " is already available.");
        }
    }
    
    public boolean isAvailable() {
        return !reserved;
    }

    public static List<Seat> createSeats(int totalSeats) {
        List<Seat> seatList = new ArrayList<>();
        for (int i = 1; i <= totalSeats; i++) {
            seatList.add(new Seat(i,false));
        }
        return seatList;
    }
}