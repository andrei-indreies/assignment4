package com.foodmanagement.presentation;

import com.foodmanagement.business.model.menu.BaseProduct;
import com.foodmanagement.business.model.menu.MenuItem;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import static com.foodmanagement.presentation.LabelsLibrary.ADD_LABEL;
import static com.foodmanagement.presentation.LabelsLibrary.PRODUCT_ADDED_SUCCESS;

public class AddProductUi extends ProductViewUi {
    public AddProductUi() {
        button.setText(ADD_LABEL);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                BaseProduct product = BaseProduct.builder()
                        .id(UUID.randomUUID())
                        .title(title.getText())
                        .rating(Double.parseDouble(rating.getText()))
                        .calories(Integer.parseInt(calories.getText()))
                        .proteins(Integer.parseInt(proteins.getText()))
                        .fats(Integer.parseInt(fats.getText()))
                        .sodium(Integer.parseInt(sodium.getText()))
                        .price(Double.parseDouble(price.getText()))
                        .build();

                deliveryService.createProduct(product);
                msg.setText(PRODUCT_ADDED_SUCCESS);
            }
        });
    }
}
