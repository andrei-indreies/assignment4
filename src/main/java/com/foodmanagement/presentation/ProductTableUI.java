package com.foodmanagement.presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static com.foodmanagement.presentation.LabelsLibrary.LOGOUT_LABEL;
import static com.foodmanagement.presentation.LabelsLibrary.TEXT_AREA_HEIGHT;

public class ProductTableUI extends BaseUi {

    public JTable table;
    public static final String[] header = {"Title", "Rating", "Calories", "Proteins", "Fats", "Sodium", "Price"};

    public ProductTableUI() {
        backButton.setText(LOGOUT_LABEL);

        String[][] rec = {};
        table = new JTable(new DefaultTableModel(rec, header));
        table.setBounds(50, 90, 700, TEXT_AREA_HEIGHT);
        table.setCellSelectionEnabled(true);
        table.setShowHorizontalLines(true);
        table.setGridColor(Color.orange);
        table.setRowSelectionAllowed(true);
        frame.add(table);

    }
}
