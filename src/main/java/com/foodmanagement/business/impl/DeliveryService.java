package com.foodmanagement.business.impl;

import com.foodmanagement.business.IDeliveryServiceProcessing;
import com.foodmanagement.business.model.criteria.ReportCriteria;
import com.foodmanagement.business.model.criteria.SearchCriteria;
import com.foodmanagement.business.model.menu.BaseProduct;
import com.foodmanagement.business.model.menu.MenuItem;
import com.foodmanagement.business.model.menu.Order;
import com.foodmanagement.data.FileWriterUtil;
import com.foodmanagement.uil.CsvReader;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.UUID;
import java.util.stream.Collectors;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing {

    private static final Map<Order, List<MenuItem>> orderListMap = new HashMap<>();
    private static final Map<String, MenuItem> menuMap = new HashMap<>();
    private static final Map<UUID, String> idToNameMap = new HashMap<>();

    @Override
    public void importProducts(final String fileName) {
        CsvReader.readProducts(fileName)
                .stream()
                .filter(Objects::nonNull)
                .map(this::createProduct);
    }

    @Override
    public MenuItem createProduct(final MenuItem menuItem) {
        final UUID id = UUID.randomUUID();
        menuItem.setId(id);

        menuMap.put(menuItem.getName(), menuItem);
        idToNameMap.put(id, menuItem.getName());

        return menuItem;
    }

    @Override
    public void deleteProduct(final UUID id) {
        final String nameOfItemToDelete = idToNameMap.get(id);

        menuMap.remove(nameOfItemToDelete);
        idToNameMap.remove(id);
    }

    @Override
    public MenuItem updateProduct(final MenuItem menuItem) {
        final UUID idOfUpdatedProduct = menuItem.getId();
        final String nameOfItemToUpdate = idToNameMap.get(idOfUpdatedProduct);

        menuMap.replace(nameOfItemToUpdate, menuItem);
        idToNameMap.replace(idOfUpdatedProduct, menuItem.getName());

        return menuItem;
    }

    @Override
    public void generateReport(final ReportCriteria reportCriteria) {
        if (reportCriteria.getStartHour() > 0 && reportCriteria.getEndHour() > 0) {
            final String filteredOrders = orderListMap.keySet().stream()
                    .filter(order -> order.getOrderDate().getHour() >= reportCriteria.getStartHour() &&
                            order.getOrderDate().getHour() <= reportCriteria.getEndHour())
                    .map(Order::toString)
                    .collect(Collectors.joining("\n"));

            FileWriterUtil.writeToFile("interval_report.txt", filteredOrders);
        }
        else if (reportCriteria.getNumberOfTimes() > 0) {
            final List<MenuItem> menuItems = orderListMap.values().stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            final String products = menuItems.stream()
                    .filter(menuItem -> Collections.frequency(menuItems, menuItem) >= reportCriteria.getNumberOfTimes())
                    .map(MenuItem::toString)
                    .collect(Collectors.joining("\n"));

            FileWriterUtil.writeToFile("frequency_report.txt", products);
        }
        else if (reportCriteria.getClientOrdersNumber() > 0 && reportCriteria.getOrderAmount() > 0) {

        }
        else if (reportCriteria.getOrderDate() != null) {
            final Map<String, Long> counterMap = orderListMap.entrySet().stream()
                    .filter(e -> ChronoUnit.DAYS.between(e.getKey().getOrderDate(), reportCriteria.getOrderDate()) == 0)
                    .map(Map.Entry::getValue)
                    .flatMap(List::stream)
                    .collect(Collectors.groupingBy(MenuItem::toString, Collectors.counting()));
            final String products = counterMap.entrySet().stream()
                    .map(Map.Entry::toString)
                    .collect(Collectors.joining("\n"));
            FileWriterUtil.writeToFile("specified_day_report.txt", products);
        }

    }

    @Override
    public Map<String, MenuItem> getProducts() {
        return menuMap;
    }

    @Override
    public List<MenuItem> getFilteredProducts(final SearchCriteria searchCriteria) {
        return menuMap.values().stream()
                .filter(
                        mi -> searchCriteria.getKeyword() == null || mi.getName().toLowerCase().contains(searchCriteria.getKeyword().toLowerCase())
                )
                .filter(
                        mi -> searchCriteria.getRating() == 0 || (mi instanceof BaseProduct &&
                                Double.valueOf(((BaseProduct) mi).getRating()).compareTo(searchCriteria.getRating()) >= 0)
                )
                .filter(
                        mi -> searchCriteria.getCalories() == 0 || (mi instanceof BaseProduct &&
                                Integer.valueOf(((BaseProduct) mi).getCalories()).equals(searchCriteria.getCalories()))
                )
                .filter(
                        mi -> searchCriteria.getProteins() == 0 || (mi instanceof BaseProduct &&
                                Integer.valueOf(((BaseProduct) mi).getProteins()).equals(searchCriteria.getProteins()))
                )
                .filter(
                        mi -> searchCriteria.getSodium() == 0 || (mi instanceof BaseProduct &&
                                Integer.valueOf(((BaseProduct) mi).getSodium()).equals(searchCriteria.getSodium()))
                )
                .filter(
                        mi -> searchCriteria.getFats() == 0 || (mi instanceof BaseProduct &&
                                Integer.valueOf(((BaseProduct) mi).getFats()).equals(searchCriteria.getFats()))
                )
                .filter(mi -> searchCriteria.getPrice() == 0 || (mi instanceof BaseProduct &&
                        Double.valueOf(mi.getPrice()).compareTo(searchCriteria.getPrice()) <= 0))
                .collect(Collectors.toList());
    }

    @Override
    public Order createOrder(final UUID clientId, final List<MenuItem> menuItems) {
        final Order order = Order.builder()
                .id(UUID.randomUUID())
                .clientId(clientId)
                .orderDate(LocalDateTime.now())
                .build();

        orderListMap.put(order, menuItems);

        return order;
    }
}
