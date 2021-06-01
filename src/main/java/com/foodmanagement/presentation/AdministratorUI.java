package com.foodmanagement.presentation;

import com.foodmanagement.business.IDeliveryServiceProcessing;
import com.foodmanagement.business.impl.DeliveryService;
import com.foodmanagement.business.model.menu.BaseProduct;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

import static com.foodmanagement.presentation.LabelsLibrary.*;

public class AdministratorUI extends ProductViewUi {
    protected IDeliveryServiceProcessing deliveryService;
    protected JTextField importFileName;
    public AdministratorUI(JFrame exFrame) {
        exFrame.setVisible(false);
        deliveryService = new DeliveryService();
        addAppendButton();
        addDeleteButton();
        addImportButton();
        BaseProduct baseProduct = BaseProduct.builder().title("merge").build();
        addModifyButton(baseProduct);


        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Title");
        model.addColumn("rating");
        model.addColumn("calories");
        model.addColumn("proteins");
        model.addColumn("fats");
        model.addColumn("sodium");
        model.addColumn("price");
        JTable table = new JTable(model);
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

    private void addModifyButton(BaseProduct product) {
        JButton modifyButton = InitializerUi.addButtonToFrame(frame, MODIFY_LABEL, (FRAME_WIDTH * 3/4) - (BUTTON_WIDTH/2), 200);
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ModifyProductUi modifyButton = new ModifyProductUi(product);
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
