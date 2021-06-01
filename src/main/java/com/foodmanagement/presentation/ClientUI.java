package com.foodmanagement.presentation;

import com.foodmanagement.business.IDeliveryServiceProcessing;
import com.foodmanagement.business.IUserServiceProcessing;
import com.foodmanagement.business.impl.DeliveryService;
import com.foodmanagement.business.impl.EmployeeService;
import com.foodmanagement.business.impl.UserService;
import com.foodmanagement.business.model.criteria.SearchCriteria;
import com.foodmanagement.business.model.menu.MenuItem;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.foodmanagement.presentation.InitializerUi.*;
import static com.foodmanagement.presentation.LabelsLibrary.BUTTON_HEIGHT;
import static com.foodmanagement.presentation.LabelsLibrary.TEXT_AREA_HEIGHT;

public class ClientUI extends ProductTableUI {

    protected JButton viewMenuButton;
    protected JButton search;
    protected JButton order;
    private IUserServiceProcessing userService;
    private DeliveryService deliveryService;
    private EmployeeService employeeService;
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

    private JTextArea textArea;

    public ClientUI(JFrame exFrame) {
        this.exFrame = exFrame;
        exFrame.setVisible(false);

        userService = new UserService();
        deliveryService = new DeliveryService();
        employeeService = new EmployeeService(deliveryService.getSubject());

        viewMenuButton = addButtonToFrame(frame, "View Menu", 50, 50);
        viewMenuButton.setBounds(50, 50, 150, BUTTON_HEIGHT);

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

        textArea = addJTextAreaToFrame(frame, 50, 540);
        textArea.setBounds(50, 540, 510, TEXT_AREA_HEIGHT);

        ListSelectionModel cellSelectionModel = table.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addCellSelectionModelListener(cellSelectionModel);

        order = addButtonToFrame(frame, "Order", 50, 50);
        order.setBounds(50, 760, 150, BUTTON_HEIGHT);
        addOrderEvent(order);
    }

    public void addCellSelectionModelListener(ListSelectionModel cellSelectionModel) {
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                int selectedRow = table.getSelectedRow();
                String row = table.getValueAt(selectedRow, 0).toString();

                if (!textArea.getText().contains(row)) {
                    textArea.append(row);
                    textArea.append("\n");
                }
            }
        });
    }

    public void addViewMenuEvent(JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                final List<String> content = deliveryService.getProducts().stream()
                        .map(MenuItem::toString)
                        .collect(Collectors.toList());


                DefaultTableModel model = (DefaultTableModel) table.getModel();
                for (int i = 0; i < content.size(); i++) {
                    String[] parts = content.get(i).split(",");
                    model.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]});
                }
            }
        });
    }

    public void addSearchEvent(JButton button, JTextField keyword, JTextField rating, JTextField calories,
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

                final List<String> content = deliveryService.getFilteredProducts(searchCriteria).stream()
                        .map(MenuItem::toString)
                        .collect(Collectors.toList());

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                for (String s : content) {
                    String[] parts = s.split(",");
                    model.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]});
                }
            }
        });
    }

    public void addOrderEvent(JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String text = textArea.getText();
                String[] items = text.split("\n");
                List<MenuItem> menuItems = new ArrayList<>();

                for (String item : items) {
                    menuItems.add(DeliveryService.menuMap.get(item));
                }

                deliveryService.createOrder(UUID.randomUUID(), menuItems);
            }
        });
    }
}