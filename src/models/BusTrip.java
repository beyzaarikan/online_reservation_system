package models;

import java.time.LocalDateTime;

public class BusTrip extends Trip {
    private String busNo;
    public BusTrip(String tripNo, String startPoint, String endPoint, LocalDateTime departureTime, LocalDateTime arrivalTime, double basePrice, int totalSeats, String company, String duration, String amentities, String busNo) {
        super(tripNo, startPoint, endPoint, departureTime, arrivalTime, basePrice, totalSeats, company, duration, amentities);
        this.busNo = busNo;
    }

    public String getBusNo() {
        return busNo;
    }

    public String getTripNo() {
        return tripNo;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public String getCompany() {
        return company;
    }   

    public String getDuration() {
        return duration;
    }

    public String getAmentities() {
        return amentities;
    }

    public String getTripType() {
        return "Bus";
    }
    public void initializeSeats() {
        this.seats = Seat.createSeats(totalSeats);
    }
    @Override   
    public String toString() {
        return "BusTrip{" +
                "busNo='" + busNo + '\'' +
                '}';
    }
}

