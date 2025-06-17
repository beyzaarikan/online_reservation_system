// iremmozkaynak/online_reservation_system/online_reservation_system-28ba3ad86cb9b46dda1defc47db65f71a11cf40a/src/utils/SampleDataLoader.java
package utils;

import builder.TripBuilder;
import factory.BusTripFactory;
import factory.FlightTripFactory;
import factory.TripFactoryManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import models.Trip;
import repository.TripRepository;
import service.TripService;

public class SampleDataLoader {

    public static void loadSampleTripData() {
        TripRepository tripRepository = TripRepository.getInstance();
        TripService tripService = new TripService(tripRepository); 
        TripFactoryManager factoryManager = new TripFactoryManager();

        if (tripRepository.findAll().isEmpty()) {
            try {
                factoryManager.registerFactory("Bus", new BusTripFactory());
                factoryManager.registerFactory("Flight", new FlightTripFactory());
                
                // Using Builder Pattern for cleaner trip creation
                Trip busTrip1 = new TripBuilder()
                    .tripNo("BT001")
                    .route("Istanbul", "Ankara")
                    .schedule(
                        LocalDateTime.of(2025, 6, 15, 8, 0),
                        LocalDateTime.of(2025, 6, 15, 14, 30)
                    )
                    .pricing(45.0)
                    .capacity(29)
                    .operator("Metro Turizm")
                    .duration("6h 30m")
                    .amenities("AC, WiFi, TV")
                    .vehicle("34-MT-001")
                    .type("Bus")
                    .build();
                tripService.addTrip(busTrip1);

                Trip busTrip2 = new TripBuilder()
                    .tripNo("BT002")
                    .route("Istanbul", "Izmir")
                    .schedule(
                        LocalDateTime.of(2025, 6, 15, 10, 15),
                        LocalDateTime.of(2025, 6, 15, 16, 45)
                    )
                    .pricing(50.0)
                    .capacity(29)
                    .operator("Varan Turizm")
                    .duration("6h 30m")
                    .amenities("AC, Refreshment")
                    .vehicle("34-VR-002")
                    .type("Bus")
                    .build();
                tripService.addTrip(busTrip2);
                
                Trip busTrip3 = new TripBuilder()
                    .tripNo("BT003")
                    .route("Istanbul", "Ankara")
                    .schedule(
                        LocalDateTime.of(2025, 6, 15, 14, 0),
                        LocalDateTime.of(2025, 6, 15, 20, 30)
                    )
                    .pricing(45.0)
                    .capacity(29)
                    .operator("Kamil Koç")
                    .duration("6h 30m")
                    .amenities("AC, WiFi")
                    .vehicle("34-KK-003")
                    .type("Bus")
                    .build();
                tripService.addTrip(busTrip3);
                
                Trip busTrip4 = new TripBuilder()
                    .tripNo("BT004")
                    .route("Istanbul", "Ankara")
                    .schedule(
                        LocalDateTime.of(2025, 6, 15, 18, 30),
                        LocalDateTime.of(2025, 6, 16, 1, 0)
                    )
                    .pricing(50.0)
                    .capacity(29)
                    .operator("Pamukkale Turizm")
                    .duration("6h 30m")
                    .amenities("AC, TV, Meal")
                    .vehicle("34-PK-004")
                    .type("Bus")
                    .build();
                tripService.addTrip(busTrip4);
                
                Trip busTrip5 = new TripBuilder()
                    .tripNo("BT005")
                    .route("Istanbul", "Ankara")
                    .schedule(
                        LocalDateTime.of(2025, 6, 15, 22, 0),
                        LocalDateTime.of(2025, 6, 16, 4, 30)
                    )
                    .pricing(45.0)
                    .capacity(29)
                    .operator("Ulusoy")
                    .duration("6h 30m")
                    .amenities("AC, WiFi")
                    .vehicle("34-UL-005")
                    .type("Bus")
                    .build();
                tripService.addTrip(busTrip5);
                
                Trip busTrip6 = new TripBuilder()
                    .tripNo("BT006")
                    .route("Ankara", "Izmir")
                    .schedule(
                        LocalDateTime.of(2025, 6, 15, 9, 0),
                        LocalDateTime.of(2025, 6, 15, 16, 0)
                    )
                    .pricing(50.0)
                    .capacity(29)
                    .operator("Metro Turizm")
                    .duration("7h 0m")
                    .amenities("AC, WiFi, TV")
                    .vehicle("06-MT-006")
                    .type("Bus")
                    .build();
                tripService.addTrip(busTrip6);
                
                Trip busTrip7 = new TripBuilder()
                    .tripNo("BT007")
                    .route("Izmir", "Istanbul")
                    .schedule(
                        LocalDateTime.of(2025, 6, 15, 11, 30),
                        LocalDateTime.of(2025, 6, 15, 19, 0)
                    )
                    .pricing(70.0)
                    .capacity(29)
                    .operator("Varan Turizm")
                    .duration("7h 30m")
                    .amenities("AC, Refreshment")
                    .vehicle("35-VR-007")
                    .type("Bus")
                    .build();
                tripService.addTrip(busTrip7);

                
                Trip flightTrip1 = new TripBuilder()
                    .tripNo("FT001")
                    .route("Istanbul", "Ankara")
                    .schedule(
                        LocalDate.of(2025, 06, 18).atTime(8, 30),
                        LocalDate.of(2025, 06, 18).atTime(9, 45)
                    )
                    .pricing(150.0)
                    .capacity(150)
                    .operator("THY")
                    .duration("1h 15m")
                    .amenities("WiFi, Meal Service")
                    .vehicle("Boeing 737")
                    .type("Flight")
                    .build();
                tripService.addTrip(flightTrip1);

                Trip flightTrip2 = new TripBuilder()
                    .tripNo("FT002")
                    .route("Istanbul", "Izmir")
                    .schedule(
                        LocalDate.of(2025, 06, 18).atTime(10, 15),
                        LocalDate.of(2025, 06, 18).atTime(11, 30)
                    )
                    .pricing(120.0)
                    .capacity(150)
                    .operator("Pegasus")
                    .duration("1h 15m")
                    .amenities("WiFi, Meal Service")
                    .vehicle("Airbus A320")
                    .type("Flight")
                    .build();
                tripService.addTrip(flightTrip2);

                Trip flightTrip3 = new TripBuilder()
                    .tripNo("FT003")
                    .route("Istanbul", "Antalya")
                    .schedule(
                        LocalDate.of(2025, 06, 18).atTime(14, 0),
                        LocalDate.of(2025, 06, 18).atTime(15, 15)
                    )
                    .pricing(180.0)
                    .capacity(150)
                    .operator("SunExpress")
                    .duration("1h 15m")
                    .amenities("WiFi, Meal Service")
                    .vehicle("Boeing 7377")
                    .type("Flight")
                    .build();
                tripService.addTrip(flightTrip3);

                Trip flightTrip4 = new TripBuilder()
                    .tripNo("FT004")
                    .route("Ankara", "Bodrum")
                    .schedule(
                        LocalDate.of(2025, 06, 18).atTime(16, 30),
                        LocalDate.of(2025, 06, 18).atTime(17, 45)
                    )
                    .pricing(200.0)
                    .capacity(150)
                    .operator("THY")
                    .duration("1h 15m")
                    .amenities("WiFi, Meal Service")
                    .vehicle("Airbus A321")
                    .type("Flight")
                    .build();
                tripService.addTrip(flightTrip4);

                Trip flightTrip5 = new TripBuilder()
                    .tripNo("FT005")
                    .route("Izmir", "Trabzon")
                    .schedule(
                        LocalDate.of(2025, 06, 18).atTime(19, 0),
                        LocalDate.of(2025, 06, 18).atTime(20, 15)
                    )
                    .pricing(160.0)
                    .capacity(150)
                    .operator("Pegasus")
                    .duration("1h 15m")
                    .amenities("WiFi, Meal Service")
                    .vehicle("Boeing 737")
                    .type("Flight")
                    .build();
                tripService.addTrip(flightTrip5);

            } catch (Exception e) {
                System.err.println("Örnek veri yüklenirken hata oluştu: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Örnek seyahat verileri zaten yüklü. Tekrar yüklenmiyor.");
        }
    }
}