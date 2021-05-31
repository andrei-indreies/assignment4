package com.foodmanagement.presentation;

import com.foodmanagement.business.IUserServiceProcessing;
import com.foodmanagement.business.impl.UserService;
import com.foodmanagement.business.model.user.Role;
import com.foodmanagement.business.model.user.User;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.foodmanagement.presentation.LabelsLibrary.*;

public class RegisterUi extends BaseUi {
    protected JButton register;
    protected JTextField usernameField;
    protected JPasswordField passwordField;
    protected JTextField userRole;
    protected JTextField msgPopup;
    private IUserServiceProcessing userService;

    public RegisterUi(JFrame exFrame) {
        this.exFrame = exFrame;
        exFrame.setVisible(false);

        userService = new UserService();

        usernameField = InitializerUi.addJTextFieldToFrame(frame, 175, 370);
        passwordField = InitializerUi.addJPasswordFieldToFrame(frame, 175, 400);
        userRole = InitializerUi.addJTextFieldToFrame(frame, 175, 430);
        register = InitializerUi.addButtonToFrame(frame, REGISTER_LABEL, 200, 470);
        msgPopup = InitializerUi.addJTextFieldToFrame(frame, 175, 520);
        msgPopup.setVisible(false);
        addRegisterEvent(register, usernameField, passwordField, userRole);
    }

    public void addRegisterEvent(JButton button, JTextField usernameField, JPasswordField passwordField,
                                 JTextField userRoleField) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String userRole = userRoleField.getText();
                User user = userService.loginUser(username, password);
                msgPopup.setVisible(true);

                if (user == null) {
                    userService.registerUser(username, password, Role.valueOf(userRole));
                    msgPopup.setText(USER_REGISTER_SUCCESS);
                } else {
                    msgPopup.setText(USER_ALREADY_EXISTS);
                }
            }
        });
    }
}
