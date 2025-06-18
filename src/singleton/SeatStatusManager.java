package singleton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SeatStatusManager {
    private static SeatStatusManager instance;
    private Map<String, Set<Integer>> occupiedSeats;
    
    private SeatStatusManager() { 
        occupiedSeats = new HashMap<>();
    }
    
    public static SeatStatusManager getInstance() { 
        if (instance == null) {
            instance = new SeatStatusManager();
        }
        return instance;
    }
    public Set<Integer> getOccupiedSeats(String busCompany) {
        return occupiedSeats.getOrDefault(busCompany, new HashSet<>());
    }
    // Tek bir seat için
    public void markSeatAsOccupied(String busCompany, int seatNumber) {
        occupiedSeats.computeIfAbsent(busCompany, k -> new HashSet<>()).add(seatNumber);
    }
    
    // Birden fazla seat için
    public void markSeatsAsOccupied(String busCompany, List<Integer> seatNumbers) {
        Set<Integer> companySeats = occupiedSeats.computeIfAbsent(busCompany, k -> new HashSet<>());
        companySeats.addAll(seatNumbers);
    }

}