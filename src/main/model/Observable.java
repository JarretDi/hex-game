package main.model;

import java.util.HashSet;
import java.util.Set;

public abstract class Observable {
    protected static Set<Observer> observers = new HashSet<>();

    public void notifyObservers(Object obj, String msg) {
        for (Observer o : observers) {
            o.update(obj, msg);
        }
    }
}
