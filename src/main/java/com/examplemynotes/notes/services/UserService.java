package com.examplemynotes.notes.services;

import java.util.List;
import com.examplemynotes.notes.models.Role;
import com.examplemynotes.notes.models.User;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getUsers(); // Its better to use pagination (LIMIT) not all the users


}
