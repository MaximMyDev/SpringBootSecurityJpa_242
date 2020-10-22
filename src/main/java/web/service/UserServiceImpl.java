package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDAO;
import web.dao.RoleDAOImpl;
import web.dao.UserDAO;
import web.dao.UserDAOImpl;
import web.model.Role;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private RoleDAO roleDAO;
    @Autowired
    public void setUserService(UserDAOImpl userDAO, RoleDAOImpl roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public List<User> allUsers() {
        return userDAO.allUsers();
    }

    @Override
    @Transactional
    public void add(User user) {
        userDAO.add(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    @Transactional
    public void edit(User user) {
        userDAO.edit(user);
    }

    @Override
    public User getById(Long id) {
        return userDAO.getById(id);
    }

    @Override
    public List<Role> allRoles() {
        return roleDAO.allRoles();
    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        return roleDAO.getRoleByRoleName(roleName);
    }

    @Override
    public User getUserByName(String name) {
        return userDAO.getUserByName(name);
    }
}
