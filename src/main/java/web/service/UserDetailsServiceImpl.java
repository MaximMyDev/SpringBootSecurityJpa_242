package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.dao.UserDAO;
import web.model.User;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static Logger log = Logger.getLogger(UserDetailsServiceImpl.class.getName());

    @Autowired
    private UserDAO userDao;
    public UserDetailsServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }

    // «Пользователь» – это просто Object. В большинстве случаев он может быть
    //  приведен к классу UserDetails.
    // Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> user = userDao.findByName(name);
        log.info("Load user by name: " + user.get().getName());
        if(user == null){
            log.info("User not found!");
            throw new UsernameNotFoundException("Username not found");
        }

        return user.get();
    }
}