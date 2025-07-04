
package repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import models.Trip;

public class TripRepository { 
    private static TripRepository instance; 
    private List<Trip> trips;

    private TripRepository() { 
        this.trips = new ArrayList<>();
    }

    public static synchronized TripRepository getInstance() {
        if (instance == null) {
            instance = new TripRepository();
        }
        return instance;
    }

    public void save(Trip trip) {
        trips.add(trip);
    }

    public Trip findByTripNo(String tripNo) { 
        for (Trip trip : trips) {
            if (trip.getTripNo().equals(tripNo)) {
                return trip;
            }
        }
        return null;
    } 
                  
    public void delete(Trip trip) {
        trips.remove(trip);
    }

    public List<Trip> findAll() {
        return new ArrayList<>(trips);
    }

    public void updateTrip(Trip trip) { 
        Trip existingTrip = findByTripNo(trip.getTripNo());
        if (existingTrip != null) {
            delete(existingTrip); 
            save(trip); 
        } else {
            throw new IllegalArgumentException("Trip not found");
        }
    }
    
    public List<Trip> findTrips(String startPoint, String endPoint, LocalDateTime date) { 
        return trips.stream()
                .filter(trip -> trip.getStartPoint().equalsIgnoreCase(startPoint) &&
                                trip.getEndPoint().equalsIgnoreCase(endPoint) &&
                                trip.getDepartureTime().toLocalDate().equals(date.toLocalDate()))
                .collect(Collectors.toList());
    }

}