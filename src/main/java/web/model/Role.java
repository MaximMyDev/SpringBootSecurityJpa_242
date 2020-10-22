package web.model;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

// Этот класс реализует интерфейс GrantedAuthority, в котором необходимо переопределить только один метод getAuthority()
// (возвращает имя роли).
// Имя роли должно соответствовать шаблону: «ROLE_ИМЯ», например, ROLE_USER.
/*
INSERT INTO roles_v01 VALUES (1, 'ROLE_USER');
INSERT INTO roles_v01 VALUES (2, 'ROLE_ADMIN');
 */
@Entity
@Table(name = "roles_v1")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "role")
    private String role;

    //@ManyToMany(mappedBy = "roles")
    //private Set<User> users;
    /*
    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }
    */
    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }
}
