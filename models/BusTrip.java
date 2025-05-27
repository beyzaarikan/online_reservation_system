package models;

public class BusTrip {
    private String busNo;
    
    public BusTrip(String busNo) {
        this.busNo = busNo;
    }   
    public String getBusNo() {
        return busNo;
    }
    @Override   
    public String toString() {
        return "BusTrip{" +
                "busNo='" + busNo + '\'' +
                '}';
    }
}

