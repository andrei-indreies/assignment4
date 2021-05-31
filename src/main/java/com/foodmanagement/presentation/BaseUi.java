package com.foodmanagement.presentation;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.foodmanagement.presentation.LabelsLibrary.BACK_LABEL;

public class BaseUi {
    protected JFrame frame;
    protected JFrame exFrame;
    protected JButton backButton;

    public BaseUi() {
        frame = InitializerUi.initFrameUi();
        backButton = InitializerUi.addButtonToFrame(frame, BACK_LABEL, 450, 30);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                frame.setVisible(false);
                exFrame.setVisible(true);
            }
        });
    }
}
