package com.foodmanagement.presentation;

import com.foodmanagement.uil.Observer;
import com.foodmanagement.uil.Subject;

public class EmployeeUI extends Observer {

    public EmployeeUI(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("da");
    }
}
