package com.foodmanagement.presentation;

import javax.swing.*;

public class AdministratorUI extends BaseUi {
    public AdministratorUI(JFrame exFrame) {
        this.exFrame = exFrame;
        exFrame.setVisible(false);

        JTextField text = InitializerUi.addJTextFieldToFrame(frame, 50, 50);
        text.setText("administratorUi");
    }
}
