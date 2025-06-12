package singleton;
import models.Trip;

public class SessionManagerTrip {
    private static SessionManagerTrip instance; // Singleton örneği
    private Trip currentTrip; // Geçerli seyahat

    private SessionManagerTrip() {
        // Private constructor for Singleton
    }

    public static synchronized SessionManagerTrip getInstance() {
        if (instance == null) {
            instance = new SessionManagerTrip();
        }
        return instance;
    }

    public void setCurrentTrip(Trip trip) {
        this.currentTrip = trip;
    }

    public Trip getCurrentTrip() {
        return currentTrip;
    }

    public boolean hasCurrentTrip() {
        return currentTrip != null;
    }

    public void clearCurrentTrip() {
        this.currentTrip = null; // Geçerli seyahati temizle
    }    
}
