package models;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Trip {
    public String tripNo;
    public String startPoint;
    public String endPoint;
    protected LocalDateTime departureTime;
    protected LocalDateTime arrivalTime;
    protected double basePrice;
    protected List<Seat> seats;
    protected int totalSeats;

    public Trip(String id, String origin, String destination, LocalDateTime departureTime, 
                LocalDateTime arrivalTime, double basePrice, int totalSeats) {
        this.tripNo = id;
        this.startPoint = origin;
        this.endPoint = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.basePrice = basePrice;
        this.totalSeats = totalSeats;
        initializeSeats();
    }

    protected abstract void initializeSeats();
    public abstract String getTripType();

    // Getters and setters
    public String getTripNo() { return tripNo; }
    public String getStartPoint() { return startPoint; }
    public String getEndPoint() { return endPoint; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public double getBasePrice() { return basePrice; }
    public List<Seat> getSeats() { return seats; }
    public int getTotalSeats() { return totalSeats; }
}
