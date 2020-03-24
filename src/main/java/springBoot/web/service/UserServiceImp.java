package springBoot.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springBoot.web.dao.UserDao;
import springBoot.web.model.Role;
import springBoot.web.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    @Autowired
    private UserDao dao;

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    @Transactional
    @Override
    public boolean addUser(User user) {
        if (user.getUsername().trim().length() == 0 || user.getPassword().trim().length() == 0 || dao.isNotReg(user.getUsername())) {
            return false;
        } else {
            Set<Role> roles = new HashSet<>();
            roles.add(new Role("user"));
            user.setRoles(roles);
            dao.addUser(user);
            return true;
        }

    }

    @Transactional
    @Override
    public void removeUser(long id) {
        dao.removeUser(id);
    }

    @Transactional
    @Override
    public boolean updateUser(String name, String password, Long id) {
        if (name.trim().length() == 0 || password.trim().length() == 0) {
            return false;
        } else {
            dao.updateUser(name, password, id);
            return true;
        }

    }

    @Transactional
    @Override
    public List<User> getUserById(long id) {
        return dao.getUserById(id);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userMayBy = Optional.ofNullable(dao.getUserByName(username));
        return userMayBy.orElseThrow(IllegalAccessError::new);
    }

    // Создаем пользователя админ и юзер если еще не созданы
    // Админ имеет две роли и доступ ко всему
    // У юзера одна роль и ограниченость в доступе
    @Transactional
    @Override
    public void addAdminAndUserPanel() {
        if (!dao.isNotReg("admin")) {

            Set<Role> admin = new HashSet<>();
            admin.add(new Role("admin"));
            admin.add(new Role("user"));
            dao.addUser(new User("admin", "admin", admin));

            Set<Role> user = new HashSet<>();
            user.add(new Role("user"));
            dao.addUser(new User("user", "user", user));
        }
    }
}
