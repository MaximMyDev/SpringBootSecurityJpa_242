package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserService {
    List<User> allUsers();
    void add(User user);
    void delete(User user);
    void edit(User user);
    User getById(Long id);
    List<Role> allRoles();
    Role getRoleByRoleName(String roleName);
    User getUserByName(String name);
}
