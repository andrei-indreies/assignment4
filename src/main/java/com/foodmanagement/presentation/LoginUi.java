package com.foodmanagement.presentation;

import com.foodmanagement.business.IUserServiceProcessing;
import com.foodmanagement.business.impl.DeliveryService;
import com.foodmanagement.business.impl.UserService;
import com.foodmanagement.business.model.user.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.foodmanagement.presentation.LabelsLibrary.LOGIN_LABEL;


public class LoginUi extends BaseUi{
    protected JButton login;
    protected JTextField usernameField;
    protected JPasswordField passwordField;
    private IUserServiceProcessing userService;
    private DeliveryService deliveryService;

    public LoginUi(JFrame exFrame) {
        this.exFrame = exFrame;
        exFrame.setVisible(false);

        userService = new UserService();
        deliveryService = new DeliveryService();

        login = InitializerUi.addButtonToFrame(frame, LOGIN_LABEL, 450, 450);
        usernameField = InitializerUi.addJTextFieldToFrame(frame, 350, 370);
        passwordField = InitializerUi.addJPasswordFieldToFrame(frame, 350, 400);
        this.addLoginEvent(login, usernameField, passwordField);
    }


    public void addLoginEvent(JButton button, JTextField usernameField, JPasswordField passwordField) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                User user = userService.loginUser(username, password);

                if (user == null) {
                    return;
                }

                switch (user.getRole().name()) {
                    case "ADMINISTRATOR" -> showAdministratorUi();
                    case "EMPLOYEE" -> showEmployeeUi();
                    case "CLIENT" -> showClientUi();
                }
            }
        });
    }

    private void showAdministratorUi() {
        AdministratorUI administratorUI = new AdministratorUI(frame);
    }

    private void showEmployeeUi() {
        new EmployeeUI(deliveryService.getSubject(), frame);
    }

    private void showClientUi() {
        new ClientUI(frame);
    }
}
