package models;
import java.time.LocalDateTime; 


public class FlightTrip extends Trip {
    private String flightNo;
    public FlightTrip(String id, String origin, String destination, LocalDateTime departureTime, 
                      LocalDateTime arrivalTime, double basePrice, int totalSeats, String flightNo) {
        super(id, origin, destination, departureTime, arrivalTime, basePrice, totalSeats);
        this.flightNo = flightNo;
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
