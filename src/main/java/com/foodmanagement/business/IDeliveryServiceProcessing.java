package com.foodmanagement.business;

import com.foodmanagement.business.model.menu.MenuItem;
import com.foodmanagement.business.model.menu.Order;
import com.foodmanagement.business.model.criteria.ReportCriteria;
import com.foodmanagement.business.model.criteria.SearchCriteria;

import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Handles all operations that can be done by administrator and clients.
 */
public interface IDeliveryServiceProcessing {

    /**
     * Imports the initial set of products to populate the menu using a file.
     *
     * @param filePath The path of the file containing the set of initial products.
     */
    void importProducts(String filePath);

    /**
     * Creates a new product (base or composite product). In case of single
     * menu item, will create a base product, else will create a composite product.
     *
     * @param menuItem The list of menu items to be crated.
     * @return A promise of the created item of menu.
     */
    MenuItem createProduct(MenuItem menuItem);

    /**
     * Deletes an existing product.
     *
     * @param id The id of the product to be deleted.
     */
    void deleteProduct(UUID id);

    /**
     * Updated an existing item from menu.
     *
     * @param menuItem The data with updated item from menu.
     * @return The updated item from menu.
     */
    MenuItem updateProduct(MenuItem menuItem);

    /**
     * Generates a report based on a report criteria.
     *
     * @param reportCriteria The report criteria containing the necessary data.
     */
    void generateReport(ReportCriteria reportCriteria);

    /**
     * Retrieves the list of products from the menu.
     *
     * @return The list of products from the menu.
     */
    List<MenuItem> getProducts();

    /**
     * Retrieves a list of products based on some search criteria.
     *
     * @param searchCriteria The search criteria.
     * @return The filtered list of products.
     */
    List<MenuItem> getFilteredProducts(SearchCriteria searchCriteria);

    /**
     * Creates an order with given products.
     *
     * @param clientId The id of the client that placed the order.
     * @param menuItems The list of products.
     * @return The new created order.
     */
    Order createOrder(UUID clientId, List<MenuItem> menuItems);

}
