// iremmozkaynak/online_reservation_system/online_reservation_system-28ba3ad86cb9b46dda1defc47db65f71a11cf40a/src/repository/TripRepository.java
package repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import models.Trip;

public class TripRepository { 
    private static TripRepository instance; // Singleton örneği
    private List<Trip> trips;

    // Private constructor for Singleton pattern
    private TripRepository() { // Constructor'ı private yaptık
        this.trips = new ArrayList<>();
    }

    // Singleton erişim metodu
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
    
    public List<Trip> findByType(String tripType) {
        return trips.stream()
                .filter(trip -> trip.getTripType().equalsIgnoreCase(tripType))
                .collect(Collectors.toList());
    }

    public List<Trip> findByDate(LocalDateTime date) {
        return trips.stream()
                .filter(trip -> trip.getDepartureTime().toLocalDate().equals(date.toLocalDate()))
                .collect(Collectors.toList());
    }

    public List<Trip> findByStartPoint(String startPoint) {
        return trips.stream()
                .filter(trip -> trip.getStartPoint().equalsIgnoreCase(startPoint))
                .collect(Collectors.toList());
    }

    public List<Trip> findByEndPoint(String endPoint) {
        return trips.stream()
                .filter(trip -> trip.getEndPoint().equalsIgnoreCase(endPoint))
                .collect(Collectors.toList());
    }

    public List<Trip> findByPriceRange(double minPrice, double maxPrice) {
        return trips.stream()
                .filter(trip -> trip.getBasePrice() >= minPrice && trip.getBasePrice() <= maxPrice)
                .collect(Collectors.toList());
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
    public boolean existsByTripNo(String tripNo) {
        return trips.stream().anyMatch(trip -> trip.getTripNo().equals(tripNo));
    }
    
    public List<Trip> findTrips(String startPoint, String endPoint, LocalDateTime date) {
        return trips.stream()
                .filter(trip -> trip.getStartPoint().equalsIgnoreCase(startPoint) &&
                                trip.getEndPoint().equalsIgnoreCase(endPoint) &&
                                trip.getDepartureTime().toLocalDate().equals(date.toLocalDate()))
                .collect(Collectors.toList());
    }

}