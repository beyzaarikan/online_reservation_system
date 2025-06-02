package factory;

import models.BusTrip;
import models.FlightTrip;
import models.Trip;
import java.time.LocalDateTime;

public abstract class TripFactory {
    public abstract Trip createTrip(String id, String origin, String destination, 
                                  LocalDateTime departureTime, LocalDateTime arrivalTime, 
                                  double basePrice, int totalSeats, String vehicleNo);
    
    public abstract String getTripType();
}
