package web.dao;

import web.model.Role;

import java.util.List;

public interface RoleDAO {
        //extends JpaRepository<Role, Long> {
        Role getRoleByRoleName(String roleName);
        List<Role> allRoles();
        Role getRoleById(int id);
}
