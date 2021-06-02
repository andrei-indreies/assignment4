package com.foodmanagement.presentation;

import com.foodmanagement.business.IDeliveryServiceProcessing;
import com.foodmanagement.business.impl.DeliveryService;
import com.foodmanagement.business.model.menu.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import static com.foodmanagement.presentation.LabelsLibrary.LOGOUT_LABEL;
import static com.foodmanagement.presentation.LabelsLibrary.TEXT_AREA_HEIGHT;

public class ProductTableUI extends BaseUi {

    public JTable table;
    public static final String[] header = {"Title", "Rating", "Calories", "Proteins", "Fats", "Sodium", "Price"};
    protected DeliveryService  deliveryService;

    public ProductTableUI() {
        deliveryService = new DeliveryService();
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

    protected void addViewMenuEvent(JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ((DefaultTableModel)table.getModel()).setNumRows(0);
                final List<String> content = deliveryService.getProducts().stream()
                        .map(MenuItem::toString)
                        .collect(Collectors.toList());


                DefaultTableModel model = (DefaultTableModel) table.getModel();
                for (int i = 0; i < content.size(); i++) {
                    String[] parts = content.get(i).split(",");
                    model.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]});
                }
            }
        });
    }
}
