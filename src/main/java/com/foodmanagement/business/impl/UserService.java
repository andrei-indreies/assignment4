package com.foodmanagement.business.impl;

import com.foodmanagement.business.IUserServiceProcessing;
import com.foodmanagement.business.model.user.Role;
import com.foodmanagement.business.model.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserService implements IUserServiceProcessing {

    public static final Map<String, User> usernameToUserMap = new HashMap<>();
    public static final Map<UUID, String> userIdToUsernameMap = new HashMap<>();

    public UserService() {
        initializeSystemUsers();
    }

    private void initializeSystemUsers() {
        final UUID id = UUID.randomUUID();
        final String username = "admin";
        final User admin = User.builder()
                .id(id)
                .username(username)
                .password("admin")
                .role(Role.ADMINISTRATOR)
                .build();

        usernameToUserMap.put(username, admin);
        userIdToUsernameMap.put(id, username);
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
        if (usernameToUserMap.get(username) != null &&
                (usernameToUserMap.get(username) == null || usernameToUserMap.get(username).getPassword().equals(password))) {
            return usernameToUserMap.get(username);
        }
        return null;
    }
}
