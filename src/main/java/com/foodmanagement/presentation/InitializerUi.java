package com.foodmanagement.presentation;

import javax.swing.*;

import static com.foodmanagement.presentation.LabelsLibrary.*;

public class InitializerUi {
    public static JFrame initFrameUi() {
        JFrame f = new JFrame();
        f.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        f.setLayout(null);
        f.setVisible(true);

        return f;
    }

    public static JButton addButtonToFrame(JFrame frame, String name,  int x, int y) {
        JButton newButton = new JButton(name);
        newButton.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        frame.add(newButton);

        return newButton;
    }

    public static JTextField addJTextFieldToFrame(JFrame frame, int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        frame.add(textField);

        return textField;
    }

    public static JPasswordField addJPasswordFieldToFrame(JFrame frame, int x, int y) {
        JPasswordField pass = new JPasswordField();
        pass.setBounds(x, y, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        frame.add(pass);

        return pass;
    }
}
