package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
// переделать на репозиторий
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public Role getRoleByRoleName(String roleName) {
        return (Role) entityManager.createQuery("FROM Role WHERE role = :roleName")
                .setParameter("roleName", roleName).getSingleResult();
    }

    public Role getRoleById(int id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public List<Role> allRoles() {
        return entityManager.createQuery("select r from Role r").getResultList();
    }
}
