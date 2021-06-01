package com.foodmanagement.business.impl;

import com.foodmanagement.business.IUserServiceProcessing;
import com.foodmanagement.business.model.user.Role;
import com.foodmanagement.business.model.user.User;
import com.foodmanagement.data.Serializator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserService implements IUserServiceProcessing {

    public static Map<String, User> usernameToUserMap = new HashMap<>();
    public static Map<UUID, String> userIdToUsernameMap = new HashMap<>();

    public UserService() {
        initializeSystemUsers();

        usernameToUserMap.putAll(Serializator.getUsersMap());
        userIdToUsernameMap.putAll(Serializator.getUserIdToUsernameMap());
    }

    private void initializeSystemUsers() {
        final UUID adminId = UUID.randomUUID();
        final UUID employeeId = UUID.randomUUID();
        final String username = "admin";
        final User admin = User.builder()
                .id(adminId)
                .username(username)
                .password("admin")
                .role(Role.ADMINISTRATOR)
                .build();
        final User employee = User.builder()
                .id(employeeId)
                .username("empl")
                .password("empl")
                .role(Role.EMPLOYEE)
                .build();

        usernameToUserMap.put(username, admin);
        userIdToUsernameMap.put(adminId, username);

        usernameToUserMap.put("empl", employee);
        userIdToUsernameMap.put(employeeId, "empl");
    }

    @Override
    public User registerUser(final String username,
                             final String password,
                             final Role role) {
        final UUID id = UUID.randomUUID();
        final User user = User.builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .build();

        usernameToUserMap.put(username, user);
        userIdToUsernameMap.put(id, username);

        return user;
    }

    @Override
    public User loginUser(final String username, final String password) {
        if (usernameToUserMap.get(username) != null && usernameToUserMap.get(username).getPassword().equals(password)) {
            return usernameToUserMap.get(username);
        }
        return null;
    }
}
