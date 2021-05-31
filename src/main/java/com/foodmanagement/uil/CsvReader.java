package com.foodmanagement.uil;

import com.foodmanagement.business.model.menu.BaseProduct;
import com.foodmanagement.business.model.menu.MenuItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CsvReader {

    /**
     * Reads products from csv file and creates a set with menu items.
     *
     * @param fileName the file name containing the products.
     * @return Set of menu items. (The set was chosen to avoid possible duplicates)
     */
    public static Set<MenuItem> readProducts(String fileName) {
        String line = "";
        String splitBy = ",";
        Set<MenuItem> menuItems = new HashSet<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                String[] menuItemLine = line.split(splitBy);
                // Title,Rating,Calories,Protein,Fat,Sodium,Price
                BaseProduct product = BaseProduct.builder()
                        .id(UUID.randomUUID())
                        .title(menuItemLine[0])
                        .rating(Double.parseDouble(menuItemLine[1]))
                        .calories(Integer.parseInt(menuItemLine[2]))
                        .proteins(Integer.parseInt(menuItemLine[3]))
                        .fats(Integer.parseInt(menuItemLine[4]))
                        .sodium(Integer.parseInt(menuItemLine[5]))
                        .price(Double.parseDouble(menuItemLine[6]))
                        .build();
                menuItems.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return menuItems;
    }
}

