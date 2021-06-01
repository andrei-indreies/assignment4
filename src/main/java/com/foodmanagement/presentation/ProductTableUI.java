package com.foodmanagement.presentation;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static com.foodmanagement.presentation.LabelsLibrary.LOGOUT_LABEL;
import static com.foodmanagement.presentation.LabelsLibrary.TEXT_AREA_HEIGHT;

public class ProductTableUI extends BaseUi {

    public JTable table;
    public static final String[] header = {"Title", "Rating", "Calories", "Proteins", "Fats", "Sodium", "Price"};

    public ProductTableUI() {
        backButton.setText(LOGOUT_LABEL);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "ODI Rankings", TitledBorder.CENTER, TitledBorder.TOP));
        String[][] rec = {};
        table = new JTable(new DefaultTableModel(rec, header));
        table.setBounds(50, 90, 700, TEXT_AREA_HEIGHT);
        table.setCellSelectionEnabled(true);
        table.setShowHorizontalLines(true);
        table.setGridColor(Color.orange);
        table.setRowSelectionAllowed(true);
        panel.add(new JScrollPane(table));
        frame.add(panel);
        frame.add(table);

        ListSelectionModel cellSelectionModel = table.getSelectionModel();

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                int selectedRow = table.getSelectedRow();
                String row = null;

                for (int i = 0; i <= 6; i++) {
                    row = String.join(",", row, table.getValueAt(selectedRow, i).toString());
                }

                System.out.println(row);
            }
        });

    }
}
