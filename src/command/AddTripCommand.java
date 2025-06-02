package command;
import models.*;
import service.*;

public class AddTripCommand implements Command {
    private TripService tripService;
    private Trip trip;
    
    public AddTripCommand(TripService tripService, Trip trip) {
        this.tripService = tripService;
        this.trip = trip;
    }
    
    @Override
    public void execute() {
        tripService.addTrip(trip);
        System.out.println("Trip added: " + trip.getTripNo());
    }
    
    @Override
    public void undo() {
        tripService.deleteTrip(trip.getTripNo());
        System.out.println("Trip removed: " + trip.getTripNo());
    }
}
