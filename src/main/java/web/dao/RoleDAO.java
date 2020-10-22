package web.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.model.Role;

import java.util.Optional;

@Repository
public interface RoleDAO extends CrudRepository<Role, Long> {
    Optional<Role> findByRole(String role);
}
