package web.dao;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    List<User> listUsers();
    User findUserByName(String name);
    void updateUser(User user);
    void deleteUser(User user);
    User getById(Long id);
    void setRole(User user, Long role);

}
