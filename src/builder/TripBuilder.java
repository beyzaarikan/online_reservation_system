package builder;

import java.time.LocalDateTime;
import models.Trip;
import factory.TripFactoryManager;

public class TripBuilder {
    private String tripNo;
    private String startPoint;
    private String endPoint;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double basePrice;
    private int totalSeats;
    private String company;
    private String duration;
    private String amenities;
    private String vehicleNo;
    private String tripType;
    
    private TripFactoryManager factoryManager;
    
    public TripBuilder() {
        this.factoryManager = new TripFactoryManager();
    }
    
    public TripBuilder tripNo(String tripNo) {
        this.tripNo = tripNo;
        return this;
    }
    
    public TripBuilder route(String startPoint, String endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        return this;
    }
    
    public TripBuilder schedule(LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        return this;
    }
    
    public TripBuilder pricing(double basePrice) {
        this.basePrice = basePrice;
        return this;
    }
    
    public TripBuilder capacity(int totalSeats) {
        this.totalSeats = totalSeats;
        return this;
    }
    
    public TripBuilder operator(String company) {
        this.company = company;
        return this;
    }
    
    public TripBuilder duration(String duration) {
        this.duration = duration;
        return this;
    }
    
    public TripBuilder amenities(String amenities) {
        this.amenities = amenities;
        return this;
    }
    
    public TripBuilder vehicle(String vehicleNo) {
        this.vehicleNo = vehicleNo;
        return this;
    }
    
    public TripBuilder type(String tripType) {
        this.tripType = tripType;
        return this;
    }
    
    public Trip build() {
        validateRequiredFields();
        return factoryManager.getFactory(tripType).createTrip(
            tripNo, startPoint, endPoint, departureTime, arrivalTime,
            basePrice, totalSeats, company, duration, amenities, vehicleNo
        );
    }
    
    private void validateRequiredFields() {
        if (tripNo == null || tripNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Trip number is required");
        }
        if (startPoint == null || startPoint.trim().isEmpty()) {
            throw new IllegalArgumentException("Start point is required");
        }
        if (endPoint == null || endPoint.trim().isEmpty()) {
            throw new IllegalArgumentException("End point is required");
        }
        if (departureTime == null) {
            throw new IllegalArgumentException("Departure time is required");
        }
        if (arrivalTime == null) {
            throw new IllegalArgumentException("Arrival time is required");
        }
        if (company == null || company.trim().isEmpty()) {
            throw new IllegalArgumentException("Company is required");
        }
        if (tripType == null || tripType.trim().isEmpty()) {
            throw new IllegalArgumentException("Trip type is required");
        }
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Base price must be positive");
        }
        if (totalSeats <= 0) {
            throw new IllegalArgumentException("Total seats must be positive");
        }
    }
}