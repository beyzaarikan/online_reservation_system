package models;
import java.time.LocalDateTime; 


public class FlightTrip extends Trip {
    private String flightNo;
    public FlightTrip(String tripNo, String startPoint, String endPoint, LocalDateTime departureTime, LocalDateTime arrivalTime, double basePrice, int totalSeats, String company, String duration, String amentities, String flightNo) {
        super(tripNo, startPoint, endPoint, departureTime, arrivalTime, basePrice, totalSeats, company, duration, amentities);
        this.flightNo = flightNo;
        // Remove the hardcoded totalSeats=150 line - use the parameter instead
    }
    public String getFlightNo() {
        return flightNo;
    }
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }
    public String getTripType() {
        return "Flight";
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
    public void initializeSeats() {
        // Initialize seats specific to flight trips
        this.seats = Seat.createSeats(totalSeats);
    }
    
    @Override   
    public String toString() {
        return "FlightTrip{" +
                "flightNo='" + flightNo + '\'' +
                '}';
    }
}