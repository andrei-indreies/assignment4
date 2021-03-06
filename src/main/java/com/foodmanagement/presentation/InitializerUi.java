package com.foodmanagement.presentation;

import javax.swing.*;

import static com.foodmanagement.presentation.LabelsLibrary.*;

public class InitializerUi {
    public static JFrame initFrameUi(int width, int height) {
        JFrame f = new JFrame();
        f.setSize(width, height);
        f.setLayout(null);
        f.setResizable(false);
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

    public static JLabel addJLabelToFrame(JFrame frame, int x, int y, String text) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setBounds(x, y, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        frame.add(label);

        return label;
    }

    public static JPasswordField addJPasswordFieldToFrame(JFrame frame, int x, int y) {
        JPasswordField pass = new JPasswordField();
        pass.setBounds(x, y, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        frame.add(pass);

        return pass;
    }

    public static JTextArea addJTextAreaToFrame(JFrame frame, int x, int y) {
        JTextArea textArea = new JTextArea();
        textArea.setBounds(x, y, TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
        frame.add(textArea);

        return textArea;
    }
}
