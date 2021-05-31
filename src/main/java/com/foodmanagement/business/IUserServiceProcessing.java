package com.foodmanagement.business;

import com.foodmanagement.business.model.user.Role;

public interface IUserServiceProcessing {

    /**
     * Registers user.
     *
     * @param username The username of the client.
     * @param password The password of the client.
     * @param role The role of the user.
     */
    void registerUser(String username, String password, Role role);
}
