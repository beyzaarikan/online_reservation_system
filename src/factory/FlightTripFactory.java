package factory;
import models.*;
import java.time.LocalDateTime;

public class FlightTripFactory extends TripFactory {
    @Override
    public Trip createTrip(String id, String origin, String destination, 
                          LocalDateTime departureTime, LocalDateTime arrivalTime, 
                          double basePrice, int totalSeats, String vehicleNo) {
        return new FlightTrip(id, origin, destination, departureTime, arrivalTime, 
                             basePrice, totalSeats, vehicleNo);
    }
    
    @Override
    public String getTripType() {
        return "Flight";
    }
}