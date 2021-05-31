package com.foodmanagement.business.model.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * Object containing information about the user using the system.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class User {

    UUID id;

    String username;

    String password;

    Role role;

    @Builder(toBuilder = true)
    public User(final UUID id, final String username,
                final String password, final Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
