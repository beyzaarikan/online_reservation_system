package models;

import java.time.LocalDateTime;

public class BusTrip extends Trip {
    private String busNo;
    public BusTrip(String tripNo, String startPoint, String endPoint, LocalDateTime departureTime, LocalDateTime arrivalTime, double basePrice, int totalSeats, String company, String duration, String amentities, String busNo) {
        super(tripNo, startPoint, endPoint, departureTime, arrivalTime, basePrice, totalSeats, company, duration, amentities);
        this.busNo = busNo;
    }

    public String getBusNo() {
        return busNo;
    }
    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getTripNo() {
        return tripNo;
    }
    public void setTripNo(String tripNo) {
        this.tripNo = tripNo;
    }
    public String getStartPoint() {
        return startPoint;
    }
    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }
    public String getEndPoint() {
        return endPoint;
    }
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public double getBasePrice() {
        return basePrice;
    }
    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
    public int getTotalSeats() {
        return totalSeats;
    }
    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
    public String getCompany() {
        return company;
    }   
    public void setCompany(String company) {
        this.company = company;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getAmentities() {
        return amentities;
    }
    public void setAmentities(String amentities) {
        this.amentities = amentities;
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

