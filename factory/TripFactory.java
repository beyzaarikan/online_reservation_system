package factory;

import adapter.BusTripAdapter;
import adapter.FlightTripAdapter;
import models.BusTrip;
import models.FlightTrip;
import models.Trip;

public class TripFactory {
    public static Trip createTrip(String type,String id) {
        if(type.equalsIgnoreCase("bus")){
            return new BusTripAdapter(new BusTrip(id));
        }
        if(type.equalsIgnoreCase("flight")){
            return new FlightTripAdapter(new FlightTrip(id));
        }
        return null;
    }
}
