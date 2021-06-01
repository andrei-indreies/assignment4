package com.foodmanagement.presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.foodmanagement.presentation.LabelsLibrary.*;

public class MiniUi {
    protected JFrame frame;
    protected JLabel msg;

    public MiniUi() {
        frame = InitializerUi.initFrameUi(MINI_FRAME_WIDTH, MINI_FRAME_HEIGHT);
        frame.setTitle(this.getClass().getSimpleName());
        msg = new JLabel();
        msg.setBounds((MINI_FRAME_WIDTH - 200)/2,350, 200, 30);
        frame.add(msg);
    }
}
