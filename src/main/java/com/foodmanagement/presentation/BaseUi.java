package com.foodmanagement.presentation;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.foodmanagement.presentation.LabelsLibrary.*;

public class BaseUi {
    protected JFrame frame;
    protected JFrame exFrame;
    protected JButton backButton;

    public BaseUi() {
        frame = InitializerUi.initFrameUi(FRAME_WIDTH, FRAME_HEIGHT);
        JLabel title = new JLabel(this.getClass().getSimpleName());
        title.setBounds(450, 50, 300, 30);
        frame.add(title);
        frame.setTitle(this.getClass().getSimpleName());

        backButton = InitializerUi.addButtonToFrame(frame, BACK_LABEL, 900, 30);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                frame.setVisible(false);
                exFrame.setVisible(true);
            }
        });
    }
}
