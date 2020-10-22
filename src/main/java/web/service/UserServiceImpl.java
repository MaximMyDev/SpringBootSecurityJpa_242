package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDAO;
import web.dao.UserDAO;
import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    public List<User> allUsers() {
        return (List<User>) userDAO.findAll();
    }

    @Transactional
    public void add(User user) {
        userDAO.save(user);
    }

    @Transactional
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Transactional
    public void edit(User user) {
        userDAO.save(user);
    }

    public Optional<User> getById(Long id) {
        return userDAO.findById(id);
    }

    public List<Role> allRoles() {
        return (List<Role>) roleDAO.findAll();
    }

    public Optional<Role> getRoleByRoleName(String roleName) {
        return roleDAO.findByRole(roleName);
    }

    public Optional<User> getUserByName(String name) {
        return userDAO.findByName(name);
    }
}
