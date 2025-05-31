package service;
import repository.*;
import models.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TripService {
    private TripRepository tripRepository;
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }
    public List<Trip> searchTrips(String startPoint, String endPoint, LocalDateTime departureTime) {
        return tripRepository.findTrips(startPoint, endPoint, departureTime);
    }
    public List<Seat> findAvailableSeats(String tripNo) {
        Trip trip = tripRepository.findByTripNo(tripNo);
        if (trip != null) {
            return trip.getSeats().stream()
                .filter(Seat::isAvailable)
                .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public void addTrip(Trip trip) { //admin
        if (tripRepository.findByTripNo(trip.getTripNo()) != null) {
            throw new IllegalArgumentException("Trip already exists");
        }
        tripRepository.save(trip);
    }

    public void deleteTrip(String tripNo) { //admin
        Trip trip = tripRepository.findByTripNo(tripNo);
        if (trip == null) {
            throw new IllegalArgumentException("Trip not found");
        }
        tripRepository.delete(trip);
    }

    public double calculatePrice(Reservation reservation) {
        int seatCount = reservation.getSeatCount();
        return reservation.getTrip().getBasePrice() * seatCount;
    }
    
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }
    public Trip findTripByNo(String tripNo) {
        return tripRepository.findByTripNo(tripNo);
    }
    public void updateTrip(Trip trip) { //admin
        Trip existingTrip = tripRepository.findByTripNo(trip.getTripNo());
        if (existingTrip == null) {
            throw new IllegalArgumentException("Trip not found");
        }
        tripRepository.save(trip);
    }

    public List<Trip> filterTripsByType(String tripType) {
        return tripRepository.findByType(tripType);
    }

    public List<Trip> filterTripsByDate(LocalDateTime date) {
        return tripRepository.findByDate(date);
    }

    public List<Trip> filterTripsByPriceRange(double minPrice, double maxPrice) {
        return tripRepository.findByPriceRange(minPrice, maxPrice);
    }

    public List<Trip> filterTripsByStartPoint(String startPoint) {
        return tripRepository.findByStartPoint(startPoint);
    }
    public List<Trip> filterTripsByEndPoint(String endPoint) {
        return tripRepository.findByEndPoint(endPoint);
    }
}
