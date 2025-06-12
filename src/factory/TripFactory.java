package factory;

import models.BusTrip;
import models.FlightTrip;
import models.Trip;
import java.time.LocalDateTime;

public abstract class TripFactory {
    public abstract Trip createTrip(String tripNo, String startPoint, String endPoint, LocalDateTime departureTime, LocalDateTime arrivalTime, double basePrice, int totalSeats, String company, String duration, String amentities, String busNo);
    
    public abstract String getTripType();
}
