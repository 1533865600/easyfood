package com.medical.ehr;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题：管理观察者，发送通知
 */
public class MedicalSubject {
    private List<MedicalObserver> observers = new ArrayList<>();

    public void registerObserver(MedicalObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(MedicalObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(NotificationEvent event) {
        for (MedicalObserver observer : observers) {
            observer.update(event);
        }
    }
}