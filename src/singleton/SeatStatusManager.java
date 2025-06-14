package singleton;

import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SeatStatusManager {
    private static SeatStatusManager instance;
    // Koltuk doluluk bilgileri sadece bellekte tutulacak
    private Map<String, Set<Integer>> occupiedSeats; // busCompany -> occupied seat numbers
    
    private SeatStatusManager() {
        occupiedSeats = new HashMap<>();
        // Dosyadan yükleme işlemi artık yok
    }
    
    public static SeatStatusManager getInstance() {
        if (instance == null) {
            instance = new SeatStatusManager();
        }
        return instance;
    }
    
    // Belirli bir bus company için occupied seat'leri al
    public Set<Integer> getOccupiedSeats(String busCompany) {
        return occupiedSeats.getOrDefault(busCompany, new HashSet<>());
    }
    
    // Seat'i occupied olarak işaretle
    public void markSeatAsOccupied(String busCompany, int seatNumber) {
        occupiedSeats.computeIfAbsent(busCompany, k -> new HashSet<>()).add(seatNumber);
        // Dosyaya kaydetme işlemi artık yok
    }
    
    // Birden fazla seat'i occupied olarak işaretle
    public void markSeatsAsOccupied(String busCompany, List<Integer> seatNumbers) {
        Set<Integer> companySeats = occupiedSeats.computeIfAbsent(busCompany, k -> new HashSet<>());
        companySeats.addAll(seatNumbers);
        // Dosyaya kaydetme işlemi artık yok
    }
    
    // Seat'in occupied olup olmadığını kontrol et
    public boolean isSeatOccupied(String busCompany, int seatNumber) {
        return occupiedSeats.getOrDefault(busCompany, new HashSet<>()).contains(seatNumber);
    }
    
    // Tüm koltuk doluluk verilerini temizlemek isterseniz bu metodu kullanabilirsiniz
    public void clearAllOccupiedSeats() {
        occupiedSeats.clear();
    }
    
    // Belirli bir otobüs şirketinin koltuk doluluk verilerini temizlemek isterseniz
    public void clearOccupiedSeatsForCompany(String busCompany) {
        occupiedSeats.remove(busCompany);
    }
}