package models;

public abstract class Trip {
    public String tripNo;
    public String startPoint;
    public String endPoint;
    public String date;

    public Trip(String tripNo, String startPoint, String endPoint, String date) {
        this.tripNo = tripNo;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.date = date;
    }
    public String getTripNo() {
        return tripNo;
    }
}
