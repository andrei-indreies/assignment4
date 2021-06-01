package com.foodmanagement.uil;

import com.foodmanagement.business.model.menu.MenuItem;
import com.foodmanagement.business.model.menu.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Subject {

    private final List<Observer> observers = new ArrayList<Observer>();
    private Order order;
    private List<MenuItem> menuItems;

    public String getOrder() {
        return String.join("\n", this.order.toString(), this.menuItems.stream()
                .map(MenuItem::toString)
                .collect(Collectors.joining(",")));
    }

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
