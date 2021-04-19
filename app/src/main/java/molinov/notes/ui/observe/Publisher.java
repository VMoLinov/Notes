package molinov.notes.ui.observe;

import java.util.ArrayList;
import java.util.List;

import molinov.notes.ui.data.Notes;

public class Publisher {

    private List<Observer> observers;

    public Publisher() {
        observers = new ArrayList<>();
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifySingle(Notes note) {
        for (Observer observer : observers) {
            observer.updateCardData(note);
            unsubscribe(observer);
        }
    }
}
