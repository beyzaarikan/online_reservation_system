package singleton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SeatStatusManager {
    private static SeatStatusManager instance;
    private Map<String, Set<Integer>> occupiedSeats; // busCompany -> occupied seat numbers
    
    private SeatStatusManager() { // Singleton yapıcı metot
        occupiedSeats = new HashMap<>();
    }
    
    public static SeatStatusManager getInstance() { 
        if (instance == null) {
            instance = new SeatStatusManager();
        }
        return instance;
    }
    
    // Belirli bir bus company için occupied seat'leri alıyoruz
    public Set<Integer> getOccupiedSeats(String busCompany) {
        return occupiedSeats.getOrDefault(busCompany, new HashSet<>());
    }
    
    // Seat'i occupied olarak işaretleme
    public void markSeatAsOccupied(String busCompany, int seatNumber) {
        occupiedSeats.computeIfAbsent(busCompany, k -> new HashSet<>()).add(seatNumber);
    }
    
    // Birden fazla seat'i occupied olarak işaretleme
    public void markSeatsAsOccupied(String busCompany, List<Integer> seatNumbers) {
        Set<Integer> companySeats = occupiedSeats.computeIfAbsent(busCompany, k -> new HashSet<>());
        companySeats.addAll(seatNumbers);
    }
    
    // Seat'in occupied olup olmadığını kontrol etme
    public boolean isSeatOccupied(String busCompany, int seatNumber) {
        return occupiedSeats.getOrDefault(busCompany, new HashSet<>()).contains(seatNumber);
    }
    
    // Tüm koltuk doluluk verilerini temizleme methodu
    public void clearAllOccupiedSeats() {
        occupiedSeats.clear();
    }
    
}