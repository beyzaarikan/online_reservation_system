package observer;

import java.util.ArrayList;

public class SeatManager implements Observer {
    private ArrayList<Observer> observers=new ArrayList<>();

    public void addObserver(Observer observer) { // Add an observer to the list
        observers.add(observer);
    }
    public void removeObserver(Observer observer) { // Remove an observer from the list
        observers.remove(observer);
    }
    @Override
    public void update() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
