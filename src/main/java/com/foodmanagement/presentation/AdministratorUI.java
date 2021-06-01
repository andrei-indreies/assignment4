package com.foodmanagement.presentation;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.foodmanagement.presentation.LabelsLibrary.*;

public class AdministratorUI extends BaseUi {
    public AdministratorUI(JFrame exFrame) {
        this.exFrame = exFrame;
        exFrame.setVisible(false);
        backButton.setText(LOGOUT_LABEL);
        addAppendButton();
        addDeleteButton();
        addModifyButton();
        addImportButton();
    }

    public void addAppendButton() {
        JButton addButton = InitializerUi.addButtonToFrame(frame, ADD_LABEL, (FRAME_WIDTH/4) - (BUTTON_WIDTH/2), 200);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                AddProductUi addProductUi = new AddProductUi();
            }
        });
    }

    public void addDeleteButton() {
        JButton deleteButton = InitializerUi.addButtonToFrame(frame, DELETE_LABEL, (FRAME_WIDTH/2) - (BUTTON_WIDTH/2), 200);
    }

    public void addModifyButton() {
        JButton modifyButton = InitializerUi.addButtonToFrame(frame, MODIFY_LABEL, (FRAME_WIDTH * 3/4) - (BUTTON_WIDTH/2), 200);
    }

    public void addImportButton() {
        JButton importButton = InitializerUi.addButtonToFrame(frame, IMPORT_LABEL, 50, 30);
    }
}
