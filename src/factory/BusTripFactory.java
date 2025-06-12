package factory;
import java.time.LocalDateTime;
import models.*;

public class BusTripFactory extends TripFactory {
    @Override
    public Trip createTrip(String tripNo, String startPoint, 
    String endPoint, LocalDateTime departureTime, LocalDateTime arrivalTime,
     double basePrice, int totalSeats, String company, String duration, 
     String amentities, String busNo){
        return new BusTrip(tripNo, startPoint, endPoint, departureTime, arrivalTime, basePrice, totalSeats, company, duration, amentities, busNo);
    }
    
    @Override
    public String getTripType() {
        return "Bus";
    }
}
