package com.foodmanagement.presentation;

import com.foodmanagement.business.IDeliveryServiceProcessing;
import com.foodmanagement.business.IUserServiceProcessing;
import com.foodmanagement.business.impl.DeliveryService;
import com.foodmanagement.business.impl.UserService;
import com.foodmanagement.business.model.criteria.SearchCriteria;
import com.foodmanagement.business.model.menu.MenuItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;

import static com.foodmanagement.presentation.InitializerUi.*;
import static com.foodmanagement.presentation.LabelsLibrary.BUTTON_HEIGHT;
import static com.foodmanagement.presentation.LabelsLibrary.TEXT_AREA_HEIGHT;

public class ClientUI extends BaseUi {

    protected JButton viewMenuButton;
    protected JButton search;
    protected JTextArea menuTextArea;
    private IUserServiceProcessing userService;
    private IDeliveryServiceProcessing deliveryService;
    private JTextField keyword;
    private JTextField rating;
    private JTextField calories;
    private JTextField proteins;
    private JTextField fats;
    private JTextField sodium;
    private JTextField price;

    private JLabel keywordLabel;
    private JLabel ratingLabel;
    private JLabel caloriesLabel;
    private JLabel proteinsLabel;
    private JLabel fatsLabel;
    private JLabel sodiumLabel;
    private JLabel priceLabel;

    public ClientUI(JFrame exFrame) {
        userService = new UserService();
        deliveryService = new DeliveryService();

        this.exFrame = exFrame;
        exFrame.setVisible(false);

        viewMenuButton = addButtonToFrame(frame, "View Menu", 50, 50);
        viewMenuButton.setBounds(50, 50, 150, BUTTON_HEIGHT);

        menuTextArea = addJTextAreaToFrame(frame, 50, 90);
        menuTextArea.setBounds(50, 90, 700, TEXT_AREA_HEIGHT);
        addViewMenuEvent(viewMenuButton);

        keywordLabel = addJLabelToFrame(frame, 50, 300, "keyword");
        keyword = addJTextFieldToFrame(frame, 370, 300);

        ratingLabel = addJLabelToFrame(frame, 50, 330, "rating");
        rating = addJTextFieldToFrame(frame, 370, 330);

        caloriesLabel = addJLabelToFrame(frame, 50, 360, "calories");
        calories = addJTextFieldToFrame(frame, 370, 360);

        proteinsLabel = addJLabelToFrame(frame, 50, 390, "proteins");
        proteins = addJTextFieldToFrame(frame, 370, 390);

        fatsLabel = addJLabelToFrame(frame, 50, 420, "fats");
        fats = addJTextFieldToFrame(frame, 370, 420);

        sodiumLabel = addJLabelToFrame(frame, 50, 450, "sodium");
        sodium = addJTextFieldToFrame(frame, 370, 450);

        priceLabel = addJLabelToFrame(frame, 50, 480, "price");
        price = addJTextFieldToFrame(frame, 370, 480);

        search = addButtonToFrame(frame, "Search", 50, 510);
        search.setBounds(50, 510, 150, BUTTON_HEIGHT);
        addSearchEvent(search, keyword, rating, calories, proteins, fats, sodium, price);
    }

    public void addViewMenuEvent(JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                final String content = deliveryService.getProducts().stream()
                        .map(MenuItem::toString)
                        .collect(Collectors.joining("\n"));

                menuTextArea.append(content);
            }
        });
    }

    public void addSearchEvent(JButton button, JTextField keyword,JTextField rating, JTextField calories,
                               JTextField proteins, JTextField fats, JTextField sodium,
                               JTextField price) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                final SearchCriteria searchCriteria = SearchCriteria.builder()
                        .keyword(keyword.getText())
                        .rating(!rating.getText().isBlank() ? Double.parseDouble(rating.getText()) : 0)
                        .calories(!calories.getText().isBlank() ? Integer.parseInt(calories.getText()) : 0)
                        .proteins(!proteins.getText().isBlank() ? Integer.parseInt(proteins.getText()) : 0)
                        .fats(!fats.getText().isBlank() ? Integer.parseInt(fats.getText()) : 0)
                        .sodium(!sodium.getText().isBlank() ? Integer.parseInt(sodium.getText()) : 0)
                        .price(!price.getText().isBlank() ? Double.parseDouble(price.getText()) : 0)
                        .build();

                final String content = deliveryService.getFilteredProducts(searchCriteria).stream()
                        .map(MenuItem::toString)
                        .collect(Collectors.joining("\n"));

                menuTextArea.selectAll();
                menuTextArea.replaceSelection("");
                menuTextArea.append(content);
            }
        });
    }
}