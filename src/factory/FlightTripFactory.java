package factory;
import java.time.LocalDateTime;
import models.*;

public class FlightTripFactory extends TripFactory {
    @Override
    public Trip createTrip(String tripNo, String startPoint, String endPoint, LocalDateTime departureTime, LocalDateTime arrivalTime, double basePrice, int totalSeats, String company, String duration, String amentities, String flightNo) {
        return new FlightTrip(tripNo, startPoint, endPoint, departureTime, arrivalTime, 
                             basePrice, totalSeats, company, duration, amentities, flightNo);
    }
    
    @Override
    public String getTripType() {
        return "Flight";
    }
}