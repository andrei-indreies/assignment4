package com.foodmanagement.business;

import com.foodmanagement.business.model.user.Role;
import com.foodmanagement.business.model.user.User;

public interface IUserServiceProcessing {

    /**
     * Registers user.
     *
     * @param username The username of the client.
     * @param password The password of the client.
     * @param role     The role of the user.
     */
    User registerUser(String username, String password, Role role);

    /**
     * Checks if the user has correct credentials to login in the system.
     *
     * @param username The username of user.
     * @param password The password of the user.
     * @return The logged in user.
     */
    User loginUser(String username, String password);
}
