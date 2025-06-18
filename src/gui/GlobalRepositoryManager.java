package gui;
import repository.ReservationRepository;


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