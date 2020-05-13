package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    @Qualifier("userDaoImpl")
    private UserDao userDao;

    @Autowired
    PasswordEncoder noOpPasswordEncoder;

    @Transactional
    @Override
    public void add(User user) {
        user.setPassword(noOpPasswordEncoder.encode(user.getPassword()));
        user.setRoles(user.getRoles());
        userDao.add(user);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getById(id);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Transactional
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(userDao.getById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findUserByName(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public void setRole(User user,Role role) {
        userDao.setRole(user, role);
    }
}
