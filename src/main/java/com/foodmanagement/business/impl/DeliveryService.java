package com.foodmanagement.business.impl;

import com.foodmanagement.business.IDeliveryServiceProcessing;
import com.foodmanagement.business.model.criteria.ReportCriteria;
import com.foodmanagement.business.model.criteria.SearchCriteria;
import com.foodmanagement.business.model.menu.BaseProduct;
import com.foodmanagement.business.model.menu.MenuItem;
import com.foodmanagement.business.model.menu.Order;
import com.foodmanagement.data.FileWriterUtil;
import com.foodmanagement.data.Serializator;
import com.foodmanagement.presentation.EmployeeUI;
import com.foodmanagement.uil.CsvReader;
import com.foodmanagement.uil.Subject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeliveryService extends Observable implements IDeliveryServiceProcessing {

    public static Map<Order, List<MenuItem>> orderListMap = new HashMap<>();
    public static Map<String, MenuItem> menuMap = new HashMap<>();
    public static Map<UUID, String> idToNameMap = new HashMap<>();

    private Subject subject;
    private EmployeeUI employeeUI;

    public DeliveryService() {
        subject = new Subject();

        orderListMap.putAll(Serializator.getOrders());
        menuMap.putAll(Serializator.getMenu());
        idToNameMap.putAll(Serializator.getIdToNameItemMap());
        importProducts("products.csv");
    }

    public boolean wellFormed() {
        return orderListMap.keySet().stream().allMatch(Order::wellFormed) &&
                menuMap.values().stream().allMatch(MenuItem::wellFormed);
    }

    @Override
    public void importProducts(final String fileName) {
        assert fileName != null;

        CsvReader.readProducts(fileName)
                .stream()
                .filter(Objects::nonNull)
                .forEach(this::createProduct);
    }

    @Override
    public MenuItem createProduct(final MenuItem menuItem) {
        assert menuItem != null;

        final UUID id = UUID.randomUUID();
        menuItem.setId(id);
        menuItem.setName(((BaseProduct) menuItem).getTitle());

        menuMap.put(menuItem.getName(), menuItem);
        idToNameMap.put(id, menuItem.getName());

        return menuItem;
    }

    @Override
    public MenuItem getProductByName(String name) {
        MenuItem menuItem =  menuMap.get(name);

        return menuItem;
    }

    @Override
    public void deleteProduct(final UUID id) {
        assert id != null;

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
        assert reportCriteria != null;

        if (reportCriteria.getStartHour() > 0 && reportCriteria.getEndHour() > 0) {
            generateIntervalReport(reportCriteria);
        } else if (reportCriteria.getNumberOfTimes() > 0) {
            generateFrequencyReport(reportCriteria);
        } else if (reportCriteria.getClientOrdersNumber() > 0 && reportCriteria.getOrderAmount() > 0) {
            generateClientsReport(reportCriteria);
        } else if (reportCriteria.getOrderDate() != null) {
            generateSpecificDayReport(reportCriteria);
        }
    }

    private void generateIntervalReport(ReportCriteria reportCriteria) {
        final String filteredOrders = orderListMap.keySet().stream()
                .filter(order -> order.getOrderDate().getHour() >= reportCriteria.getStartHour() &&
                        order.getOrderDate().getHour() <= reportCriteria.getEndHour())
                .map(Order::toString)
                .collect(Collectors.joining("\n"));

        FileWriterUtil.writeToFile("interval_report.txt", filteredOrders);
    }

    private void generateFrequencyReport(ReportCriteria reportCriteria) {
        final List<MenuItem> menuItems = orderListMap.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        final String products = menuItems.stream()
                .filter(menuItem -> Collections.frequency(menuItems, menuItem) >= reportCriteria.getNumberOfTimes())
                .collect(Collectors.toSet())
                .stream()
                .map(MenuItem::toString)
                .collect(Collectors.joining("\n"));

        FileWriterUtil.writeToFile("frequency_report.txt", products);
    }

    private void generateClientsReport(ReportCriteria reportCriteria) {
        final List<UUID> clientIds = orderListMap.keySet().stream()
                .map(Order::getClientId)
                .collect(Collectors.toList());
        final Set<UUID> filteredClientIds = orderListMap.keySet().stream()
                .filter(order -> Collections.frequency(clientIds, order.getClientId()) >= reportCriteria.getClientOrdersNumber() &&
                        getOrderPrice(order) > reportCriteria.getOrderAmount())
                .map(Order::getClientId)
                .collect(Collectors.toSet());
        final String usernames = filteredClientIds.stream()
                .map(UserService.userIdToUsernameMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("\n"));

        FileWriterUtil.writeToFile("clients_report.txt", usernames);
    }

    private double getOrderPrice(final Order order) {
        final List<MenuItem> items = orderListMap.get(order);

        return items.stream()
                .mapToDouble(MenuItem::computePrice)
                .sum();
    }

    private void generateSpecificDayReport(ReportCriteria reportCriteria) {
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

    @Override
    public List<MenuItem> getProducts() {
        return new ArrayList<>(menuMap.values());
    }

    @Override
    public List<MenuItem> getFilteredProducts(final SearchCriteria searchCriteria) {
        assert searchCriteria != null;

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

        assert wellFormed();

        System.out.println("Creating order..." + order.toString());
        orderListMap.put(order, menuItems);

        subject.setOrder(order, menuItems);

        final String products = menuItems.stream()
                .map(MenuItem::toString)
                .collect(Collectors.joining("\n"));
        final double price = getOrderPrice(order);
        final String content = String.join("\n", products, String.valueOf(price));

        FileWriterUtil.writeToFile("bill.txt", content);

        return order;
    }
}
