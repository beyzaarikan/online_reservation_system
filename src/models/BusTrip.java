package models;

import java.time.LocalDateTime;

public class BusTrip extends Trip {
    private String busNo;

    public BusTrip(String id, String origin, String destination, LocalDateTime departureTime, 
                   LocalDateTime arrivalTime, double basePrice, int totalSeats, String busNo) {
        super(id, origin, destination, departureTime, arrivalTime, basePrice, totalSeats);
        this.busNo = busNo;
    }

    public String getBusNo() {
        return busNo;
    }
    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }
    public String getTripType() {
        return "Bus";
    }
    public void initializeSeats() {
        // Initialize seats specific to bus trips
        this.seats = Seat.createSeats(totalSeats);
    }
    @Override   
    public String toString() {
        return "BusTrip{" +
                "busNo='" + busNo + '\'' +
                '}';
    }
}

