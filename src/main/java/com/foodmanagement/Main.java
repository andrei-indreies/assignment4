package com.foodmanagement;

import com.foodmanagement.business.impl.DeliveryService;
import com.foodmanagement.data.Serializator;
import com.foodmanagement.presentation.InitializerUi;
import com.foodmanagement.presentation.LoginUi;
import com.foodmanagement.presentation.RegisterUi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.foodmanagement.presentation.LabelsLibrary.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = InitializerUi.initFrameUi(MINI_FRAME_WIDTH, MINI_FRAME_HEIGHT);
        initRegisterLoginToFrameUi(frame);
        frame.setTitle("Main");

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Serializator.saveOrders(DeliveryService.orderListMap);
                Serializator.saveMenu(DeliveryService.menuMap);
                Serializator.saveIdToNameItemMap(DeliveryService.idToNameMap);
            }
        });

    }

    public static void initRegisterLoginToFrameUi(JFrame frame) {
        JButton loginButton = InitializerUi.addButtonToFrame(frame, LOGIN_LABEL, (MINI_FRAME_WIDTH-BUTTON_HEIGHT)/2, 200);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                LoginUi login = new LoginUi(frame);
            }
        });
        JButton registerButton = InitializerUi.addButtonToFrame(frame, REGISTER_LABEL, (MINI_FRAME_WIDTH-BUTTON_HEIGHT)/2, 300);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                RegisterUi register = new RegisterUi(frame);
            }
        });
    }
}
