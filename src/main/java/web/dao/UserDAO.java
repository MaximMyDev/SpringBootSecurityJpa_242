package web.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.model.User;

import java.util.Optional;

@Repository
public interface UserDAO extends CrudRepository<User, Long> {
    Optional<User> findByName(String name);
    /*
    List<User> allUsers();
    void add(User user);
    void delete(User user);
    void edit(User user);
    User getById(Long id);
    User getUserByName(String name);

     */
}