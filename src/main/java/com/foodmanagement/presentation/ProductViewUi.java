package com.foodmanagement.presentation;

import com.foodmanagement.business.IDeliveryServiceProcessing;
import com.foodmanagement.business.impl.DeliveryService;

import javax.swing.*;

import static com.foodmanagement.presentation.LabelsLibrary.MINI_FRAME_WIDTH;

public class ProductViewUi extends MiniUi {
    protected JTextField title;
    protected JTextField rating;
    protected JTextField calories;
    protected JTextField proteins;
    protected JTextField fats;
    protected JTextField sodium;
    protected JButton button;
    protected IDeliveryServiceProcessing deliveryService;

    public ProductViewUi() {
        deliveryService = new DeliveryService();

        addFormLabels();

        title = InitializerUi.addJTextFieldToFrame(frame, 10, 20);
        rating = InitializerUi.addJTextFieldToFrame(frame, 10, 75);
        calories = InitializerUi.addJTextFieldToFrame(frame, 10, 125);
        proteins = InitializerUi.addJTextFieldToFrame(frame, 10, 175);
        fats = InitializerUi.addJTextFieldToFrame(frame, 10, 225);
        sodium = InitializerUi.addJTextFieldToFrame(frame, 10, 275);
        button = InitializerUi.addButtonToFrame(frame,  "Default", (MINI_FRAME_WIDTH-100)/2, 320);
    }

    public void addFormLabels() {
        JLabel title = new JLabel("Title");
        JLabel rating = new JLabel("Rating");
        JLabel calories = new JLabel("Calories");
        JLabel proteins = new JLabel("Proteins");
        JLabel fats = new JLabel("Fats");
        JLabel sodium = new JLabel("Sodium");

        title.setBounds(10, 10 , 100, 15);
        rating.setBounds(10, 60 , 100, 15);
        calories.setBounds(10, 110 , 100, 15);
        proteins.setBounds(10, 160 , 100, 15);
        fats.setBounds(10, 210 , 100, 15);
        sodium.setBounds(10, 260 , 100, 15);

        frame.add(title);
        frame.add(rating);
        frame.add(calories);
        frame.add(proteins);
        frame.add(fats);
        frame.add(sodium);
    }
}
