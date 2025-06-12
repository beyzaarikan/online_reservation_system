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
    protected String company;
    protected String duration;
    protected String amentities;

    public Trip(String tripNo, String startPoint, String endPoint, LocalDateTime departureTime, LocalDateTime arrivalTime, double basePrice, int totalSeats, String company, String duration, String amentities) {
        this.tripNo = tripNo;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.basePrice = basePrice;
        this.totalSeats = 29;
        this.company = company;
        this.duration = duration;
        this.amentities = amentities;
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
    public String getCompany() { return company; }
    public String getDuration() { return duration; }
    public String getAmentities() { return amentities; }
}
