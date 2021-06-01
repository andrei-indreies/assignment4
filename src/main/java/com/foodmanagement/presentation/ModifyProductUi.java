package com.foodmanagement.presentation;

import com.foodmanagement.business.model.menu.BaseProduct;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.foodmanagement.presentation.LabelsLibrary.*;

public class ModifyProductUi extends ProductViewUi {
    public ModifyProductUi(BaseProduct product) {
        button.setText(MODIFY_LABEL);
        fillTextBoxesWithProductData(product);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                BaseProduct updatedProduct = product.toBuilder()
                        .title(title.getText())
                        .rating(Double.parseDouble(rating.getText()))
                        .calories(Integer.parseInt(calories.getText()))
                        .proteins(Integer.parseInt(proteins.getText()))
                        .fats(Integer.parseInt(fats.getText()))
                        .sodium(Integer.parseInt(sodium.getText()))
                        .price(Double.parseDouble(price.getText()))
                        .build();

                deliveryService.updateProduct(updatedProduct);

                msg.setText(PRODUCT_MODIFIED_SUCCESS);
            }
        });
    }

    public void fillTextBoxesWithProductData(BaseProduct product) {
        title.setText(product.getTitle());
        rating.setText(String.valueOf(product.getRating()));
        calories.setText(String.valueOf(product.getCalories()));
        proteins.setText(String.valueOf(product.getProteins()));
        fats.setText(String.valueOf(product.getFats()));
        sodium.setText(String.valueOf(product.getSodium()));
        price.setText(String.valueOf(product.getPrice()));
    }
}
