package com.foodmanagement.uil;

import com.foodmanagement.business.model.menu.MenuItem;
import com.foodmanagement.business.model.menu.Order;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private final List<Observer> observers = new ArrayList<Observer>();
    private Order order;
    private List<MenuItem> menuItems;


    public void setOrder(Order order, List<MenuItem> menuItems) {
        this.order = order;
        this.menuItems = menuItems;
        notifyAllObservers();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
