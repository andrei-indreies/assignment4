package com.foodmanagement.business.impl;

import com.foodmanagement.data.FileWriterUtil;
import com.foodmanagement.uil.Observer;
import com.foodmanagement.uil.Subject;

public class EmployeeService implements Observer {

    Subject subject;

    public EmployeeService(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        FileWriterUtil.writeToFile("orders.txt", subject.getOrder());
    }
}
