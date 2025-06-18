package factory;
import java.util.HashMap;
import java.util.Map;

public class TripFactoryManager {
    // Map to hold different trip factories
    private Map<String, TripFactory> factories = new HashMap<>(); 
    
    public TripFactoryManager() {
        factories.put("Bus", new BusTripFactory());
        factories.put("Flight", new FlightTripFactory());
    }
    
    public TripFactory getFactory(String tripType) {
        TripFactory factory = factories.get(tripType);
        if (factory == null) {
            throw new IllegalArgumentException("Unsupported trip type: " + tripType); 
        } // Throw an exception if the trip type is not supported
        return factory;
    }

    // Method to register a new trip factory
    public void registerFactory(String tripType, TripFactory factory) { 
        factories.put(tripType, factory);
    }
}