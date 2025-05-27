package adapter;

import models.FlightTrip;
import models.Trip;

public class FlightTripAdapter extends Trip {
    private FlightTrip flightTrip;

    public FlightTripAdapter(FlightTrip flightTrip) {
        super(flightTrip.getFlightNo(), "Start", "End", "2025-05-27");
        this.flightTrip = flightTrip;
    }

    public FlightTrip getBusTrip() {
        return flightTrip;
    }
}
