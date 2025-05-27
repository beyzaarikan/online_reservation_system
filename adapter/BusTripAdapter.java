package adapter;

import models.BusTrip;
import models.Trip;

public class BusTripAdapter extends Trip {
    private BusTrip busTrip;

    public BusTripAdapter(BusTrip busTrip) {
        super(busTrip.getBusNo(), "Start", "End", "2025-05-27");
        this.busTrip = busTrip;
    }

    public BusTrip getBusTrip() {
        return busTrip;
    }
}