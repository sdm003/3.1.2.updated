package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        entityManager.remove(user);
    }

    @Override
    public User getById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        return entityManager.createQuery("select user from User user ", User.class).getResultList();
    }

    @Override
    public User findUserByName(String name) {
       return (User) entityManager.createQuery("SELECT user FROM User user left join fetch user.roles where user.name =:name").setParameter("name", name).getSingleResult();
    }

    @Override
    public void setRole(User user,Role role) {
        System.out.println(role.getName());
        if (role.getName().equals("ADMIN")) {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_ADMIN")));
        } if (role.getName().equals("USER")) {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        }
    }

}
