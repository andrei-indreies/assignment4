package com.foodmanagement;

import com.foodmanagement.presentation.InitializerUi;
import com.foodmanagement.presentation.LoginUi;
import com.foodmanagement.presentation.RegisterUi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.foodmanagement.presentation.LabelsLibrary.LOGIN_LABEL;
import static com.foodmanagement.presentation.LabelsLibrary.REGISTER_LABEL;

public class Main {
    public static void main(String[] args) {
        JFrame frame = InitializerUi.initFrameUi();
        initRegisterLoginToFrameUi(frame);

    }

    public static void initRegisterLoginToFrameUi(JFrame frame) {
        JButton loginButton = InitializerUi.addButtonToFrame(frame, LOGIN_LABEL, 400, 220);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                LoginUi login = new LoginUi(frame);
            }
        });
        JButton registerButton = InitializerUi.addButtonToFrame(frame, REGISTER_LABEL, 600, 220);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                RegisterUi register = new RegisterUi(frame);
            }
        });
    }
}
