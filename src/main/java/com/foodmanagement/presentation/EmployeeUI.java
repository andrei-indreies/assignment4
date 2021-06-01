package com.foodmanagement.presentation;

import com.foodmanagement.data.FileReaderUtil;

import javax.swing.*;

import static com.foodmanagement.presentation.InitializerUi.addJTextAreaToFrame;

public class EmployeeUI extends BaseUi {


    JTextArea textArea;

    public EmployeeUI(JFrame exFrame) {

        this.exFrame = exFrame;
        exFrame.setVisible(false);

        textArea = addJTextAreaToFrame(frame, 300, 100);
        textArea.append(FileReaderUtil.readFile("orders.txt"));
    }

}
