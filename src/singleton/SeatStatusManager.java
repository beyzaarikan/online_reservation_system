package singleton;

import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SeatStatusManager {
    private static SeatStatusManager instance;
    private Map<String, Set<Integer>> occupiedSeats; // busCompany -> occupied seat numbers
    private String dataFilePath = "occupied_seats.dat";
    
    private SeatStatusManager() {
        occupiedSeats = new HashMap<>();
        loadOccupiedSeats();
    }
    
    public static SeatStatusManager getInstance() {
        if (instance == null) {
            instance = new SeatStatusManager();
        }
        return instance;
    }
    
    // Occupied seat'leri dosyadan yükle
    private void loadOccupiedSeats() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFilePath))) {
            occupiedSeats = (Map<String, Set<Integer>>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Dosya yoksa boş map ile başla
            occupiedSeats = new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            occupiedSeats = new HashMap<>();
        }
    }
    
    // Occupied seat'leri dosyaya kaydet
    private void saveOccupiedSeats() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFilePath))) {
            oos.writeObject(occupiedSeats);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Belirli bir bus company için occupied seat'leri al
    public Set<Integer> getOccupiedSeats(String busCompany) {
        return occupiedSeats.getOrDefault(busCompany, new HashSet<>());
    }
    
    // Seat'i occupied olarak işaretle
    public void markSeatAsOccupied(String busCompany, int seatNumber) {
        occupiedSeats.computeIfAbsent(busCompany, k -> new HashSet<>()).add(seatNumber);
        saveOccupiedSeats();
    }
    
    // Birden fazla seat'i occupied olarak işaretle
    public void markSeatsAsOccupied(String busCompany, List<Integer> seatNumbers) {
        Set<Integer> companySeats = occupiedSeats.computeIfAbsent(busCompany, k -> new HashSet<>());
        companySeats.addAll(seatNumbers);
        saveOccupiedSeats();
    }
    
    // Seat'in occupied olup olmadığını kontrol et
    public boolean isSeatOccupied(String busCompany, int seatNumber) {
        return occupiedSeats.getOrDefault(busCompany, new HashSet<>()).contains(seatNumber);
    }
}