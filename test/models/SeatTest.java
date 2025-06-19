package models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SeatTest {
    private Seat seat;
    
    @BeforeEach
    void setUp() {
        seat = new Seat(1, false, "1A"); // Test için 1 numaralı boş koltuk oluşturuyoruz
    }
    
    @Test
    void testReserve() {
        assertTrue(seat.isAvailable()); // Başlangıçta koltuk boş olmalı
        seat.reserve(); // Koltuğu rezerve et
        assertFalse(seat.isAvailable()); // Rezervasyondan sonra koltuk dolu olmalı
    }
    
    @Test
    void testUnreserve() {
        seat.reserve(); // Önce koltuğu rezerve ediyoruz
        assertFalse(seat.isAvailable()); // Koltuk dolu olmalı
        seat.unreserve(); // Rezervasyonu iptal et
        assertTrue(seat.isAvailable()); // İptalden sonra koltuk boş olmalı
    }
    
    @Test
    void testIsAvailable() {
        assertTrue(seat.isAvailable()); // Başlangıçta koltuk boş olmalı
        seat.reserve(); // Koltuğu rezerve et
        assertFalse(seat.isAvailable()); // Rezervasyondan sonra koltuk dolu olmalı
        seat.unreserve(); // Rezervasyonu iptal et
        assertTrue(seat.isAvailable()); // İptalden sonra koltuk tekrar boş olmalı
    }
}
