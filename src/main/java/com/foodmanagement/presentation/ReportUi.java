package com.foodmanagement.presentation;

import com.foodmanagement.business.IDeliveryServiceProcessing;
import com.foodmanagement.business.impl.DeliveryService;
import com.foodmanagement.business.model.criteria.ReportCriteria;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.foodmanagement.presentation.LabelsLibrary.GENERATE_LABEL;
import static com.foodmanagement.presentation.LabelsLibrary.MINI_FRAME_WIDTH;

public class ReportUi extends MiniUi {
    protected JTextField startHour;
    protected JTextField endHour;
    protected JTextField numberOfTimes;
    protected JTextField clientOrdersNumber;
    protected JTextField orderAmount;
    protected JTextField orderDate;
    protected JButton button;
    protected IDeliveryServiceProcessing deliveryService;

    public ReportUi() {
        deliveryService = new DeliveryService();

        addFormLabels();

        startHour = InitializerUi.addJTextFieldToFrame(frame, 10, 20);
        endHour = InitializerUi.addJTextFieldToFrame(frame, 10, 75);
        numberOfTimes = InitializerUi.addJTextFieldToFrame(frame, 10, 125);
        clientOrdersNumber = InitializerUi.addJTextFieldToFrame(frame, 10, 175);
        orderAmount = InitializerUi.addJTextFieldToFrame(frame, 10, 225);
        orderDate = InitializerUi.addJTextFieldToFrame(frame, 10, 275);
        button = InitializerUi.addButtonToFrame(frame,  "Default", (MINI_FRAME_WIDTH-100)/2, 370);
        button.setText(GENERATE_LABEL);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                LocalDateTime dateTime = LocalDateTime.parse(orderDate.getText(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                ReportCriteria criteria = ReportCriteria.builder()
                        .startHour(Integer.parseInt(startHour.getText()))
                        .endHour(Integer.parseInt(endHour.getText()))
                        .numberOfTimes(Integer.parseInt(numberOfTimes.getText()))
                        .clientOrdersNumber(Integer.parseInt(clientOrdersNumber.getText()))
                        .orderAmount(Double.parseDouble(orderAmount.getText()))
                        .orderDate(dateTime)
                        .build();

                deliveryService.generateReport(criteria);
            }

        });
    }

    public void addFormLabels() {
        JLabel startHour = new JLabel("Start Hour");
        JLabel endHour = new JLabel("End Hour");
        JLabel numberOfTimes = new JLabel("Number of times");
        JLabel clientOrdersNumber = new JLabel("Client Orders number");
        JLabel orderAmount = new JLabel("Order Amount");
        JLabel orderDate = new JLabel("Order Date");

        startHour.setBounds(10, 10 , 150, 15);
        endHour.setBounds(10, 60 , 150, 15);
        numberOfTimes.setBounds(10, 110 , 150, 15);
        clientOrdersNumber.setBounds(10, 160 , 150, 15);
        orderAmount.setBounds(10, 210 , 150, 15);
        orderDate.setBounds(10, 260 , 150, 15);

        frame.add(startHour);
        frame.add(endHour);
        frame.add(numberOfTimes);
        frame.add(clientOrdersNumber);
        frame.add(orderAmount);
        frame.add(orderDate);
    }
}
