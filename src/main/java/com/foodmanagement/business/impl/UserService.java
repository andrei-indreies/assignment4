package com.foodmanagement.business.impl;

import com.foodmanagement.business.IUserServiceProcessing;
import com.foodmanagement.business.model.user.Role;
import com.foodmanagement.business.model.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserService implements IUserServiceProcessing {

    private static final Map<String, User> usernameToUserMap = new HashMap<>();
    private static final Map<UUID, String>  userIdToUsernameMap = new HashMap<>();

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
    public void registerUser(String username, String password, Role role) {
        final UUID id = UUID.randomUUID();
        final User user = User.builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .build();

        usernameToUserMap.put(username, user);
        userIdToUsernameMap.put(id, username);
    }
}
