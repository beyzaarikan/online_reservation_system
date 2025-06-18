package factory;

import java.time.LocalDateTime;
import models.Trip;

public abstract class TripFactory {
    public abstract Trip createTrip(String tripNo, String startPoint, String endPoint, LocalDateTime departureTime, LocalDateTime arrivalTime, double basePrice, int totalSeats, String company, String duration, String amentities, String No);
    
    public abstract String getTripType();
}
