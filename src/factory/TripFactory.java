package factory;

import models.BusTrip;
import models.FlightTrip;
import models.Trip;
import java.time.LocalDateTime;

public abstract class TripFactory {
    public abstract Trip createTrip(String id, String origin, String destination, 
                                  LocalDateTime departureTime, LocalDateTime arrivalTime, 
                                  double basePrice, int totalSeats);
    
    public static TripFactory getFactory(String type) {
        switch (type.toLowerCase()) {
            case "bus":
                return new BusTripFactory();
            case "flight":
                return new FlightTripFactory();
            default:
                throw new IllegalArgumentException("Unknown trip type: " + type);
        }
    }
}
