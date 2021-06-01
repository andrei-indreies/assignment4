package com.foodmanagement.presentation;

import com.foodmanagement.uil.Observer;
import com.foodmanagement.uil.Subject;

import javax.swing.*;

import static com.foodmanagement.presentation.InitializerUi.addJTextAreaToFrame;

public class EmployeeUI extends BaseUi implements Observer {

    Subject subject;

    JTextArea textArea;

    public EmployeeUI(Subject subject, JFrame exFrame) {
        this.subject = subject;
        this.subject.attach(this);

        this.exFrame = exFrame;
        exFrame.setVisible(false);

        textArea = addJTextAreaToFrame(frame, 300, 100);
    }

    @Override
    public void update() {
        textArea.append(subject.getOrder());
    }
}
