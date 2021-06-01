package com.foodmanagement.presentation;

import com.foodmanagement.business.IDeliveryServiceProcessing;
import com.foodmanagement.business.impl.DeliveryService;
import com.foodmanagement.business.model.menu.BaseProduct;
import com.foodmanagement.business.model.menu.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.foodmanagement.presentation.InitializerUi.addButtonToFrame;
import static javax.swing.JOptionPane.showMessageDialog;

import static com.foodmanagement.presentation.LabelsLibrary.*;

public class AdministratorUI extends ProductTableUI {
    protected JTextField importFileName;
    public AdministratorUI(JFrame exFrame) {
        exFrame.setVisible(false);
        addAppendButton();
        addDeleteButton();
        addImportButton();
        addModifyButton();
        addViewButton();
        table.setBounds((FRAME_WIDTH-700)/2, 450, 700, 450);
        table.setCellSelectionEnabled(false);
    }

    private void addViewButton() {
        JButton viewMenuButton = addButtonToFrame(frame, "View Menu", 400, 50);
        viewMenuButton.setBounds((FRAME_WIDTH/2) - (150/2), 150, 150, BUTTON_HEIGHT);
        addViewMenuEvent(viewMenuButton);
    }

    private void addAppendButton() {
        JButton addButton = InitializerUi.addButtonToFrame(frame, ADD_LABEL, (FRAME_WIDTH/4) - (BUTTON_WIDTH/2), 200);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                AddProductUi addProductUi = new AddProductUi();
            }
        });
    }

    private void addDeleteButton() {
        JButton deleteButton = InitializerUi.addButtonToFrame(frame, DELETE_LABEL, (FRAME_WIDTH/2) - (BUTTON_WIDTH/2), 200);
    }

    private void addModifyButton() {
        JButton modifyButton = InitializerUi.addButtonToFrame(frame, MODIFY_LABEL, (FRAME_WIDTH * 3/4) - (BUTTON_WIDTH/2), 200);
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int row = table.getSelectedRow();
                String rowData = table.getValueAt(row,0).toString();
                MenuItem menuItem = deliveryService.getProductByName(rowData);
                ModifyProductUi modifyProductUi = new ModifyProductUi((BaseProduct) menuItem);
            }
        });
    }

    private void addImportButton() {
        JButton importButton = InitializerUi.addButtonToFrame(frame, IMPORT_LABEL, 50, 30);
        selectCsvFile();
        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                deliveryService.importProducts(getCsvFileName());
                showMessageDialog(null, "Products imported!");
            }
        });
    }

    private void selectCsvFile() {
        importFileName = new JTextField();
        JLabel csvLabel = new JLabel("Insert Csv Name");
        csvLabel.setBounds(170, 0, 150, 30);
        csvLabel.setLabelFor(importFileName);
        importFileName.setBounds(160, 30, 150, 30);
        frame.add(importFileName);
        frame.add(csvLabel);
    }

    private String getCsvFileName() {
        return importFileName.getText();
    }
}
