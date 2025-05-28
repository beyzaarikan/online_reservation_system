package models;

public class FlightTrip {
    private String flightNo;

    public FlightTrip(String flightNo) {
        this.flightNo = flightNo;
    }
    public String getFlightNo() {
        return flightNo;
    }
    
    @Override   
    public String toString() {
        return "FlightTrip{" +
                "flightNo='" + flightNo + '\'' +
                '}';
    }
}
