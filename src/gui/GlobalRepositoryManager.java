package gui;
import repository.ReservationRepository;

/**
 * Singleton class to manage global repository instances
 * This ensures all parts of the application use the same repository instances
 */
public class GlobalRepositoryManager {
    private static GlobalRepositoryManager instance;
    private ReservationRepository reservationRepository;
    
    private GlobalRepositoryManager() {
        this.reservationRepository = new ReservationRepository();
    }
    
    public static synchronized GlobalRepositoryManager getInstance() {
        if (instance == null) {
            instance = new GlobalRepositoryManager();
        }
        return instance;
    }
    
    public ReservationRepository getReservationRepository() {
        return reservationRepository;
    }
}