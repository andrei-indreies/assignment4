package com.foodmanagement.data;

import com.foodmanagement.business.model.menu.MenuItem;
import com.foodmanagement.business.model.menu.Order;
import com.foodmanagement.business.model.user.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Serializator {

    public static void saveOrders(Map<Order, List<MenuItem>> orders) {
        String filename = "orders.ser";

        ObjectOutputStream oos;
        try {
            System.out.println("Saving orders...");
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(orders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<Order, List<MenuItem>> getOrders() {
        String fileName = "orders.ser";

        ObjectInputStream ois;
        Map<Order, List<MenuItem>> orders = new HashMap<>();
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            try {
                orders = (Map<Order, List<MenuItem>>) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public static void saveMenu(Map<String, MenuItem> menu) {
        String filename = "menu.ser";

        ObjectOutputStream oos;
        try {
            System.out.println("Saving menu...");
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, MenuItem> getMenu() {
        String fileName = "menu.ser";

        ObjectInputStream ois;
        Map<String, MenuItem> menu = new HashMap<>();
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            try {
                menu = (Map<String, MenuItem>) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return menu;
    }

    public static void saveIdToNameItemMap(Map<UUID, String> menu) {
        String filename = "idsToName.ser";

        ObjectOutputStream oos;
        try {
            System.out.println("Saving ids to menu items...");
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<UUID, String> getIdToNameItemMap() {
        String fileName = "menu.ser";

        ObjectInputStream ois;
        Map<UUID, String> idToNameItemMap = new HashMap<>();
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            try {
                idToNameItemMap = (Map<UUID, String>) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return idToNameItemMap;
    }

    public static void saveUsersMap(Map<String, User> users) {
        String filename = "users.ser";

        ObjectOutputStream oos;
        try {
            System.out.println("Saving users...");
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, User> getUsersMap() {
        String fileName = "users.ser";

        ObjectInputStream ois;
        Map<String, User> usersMap = new HashMap<>();
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            try {
                usersMap = (Map<String, User>) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usersMap;
    }

    public static void saveUserIdToUsernameMap(Map<UUID, String> menu) {
        String filename = "userIdToUsername.ser";

        ObjectOutputStream oos;
        try {
            System.out.println("Saving ids to to username items...");
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<UUID, String> getUserIdToUsernameMap() {
        String fileName = "userIdToUsername.ser";

        ObjectInputStream ois;
        Map<UUID, String> idToNameItemMap = new HashMap<>();
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            try {
                idToNameItemMap = (Map<UUID, String>) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return idToNameItemMap;
    }
}
