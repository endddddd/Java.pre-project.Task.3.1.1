package springBoot.web.service;

import springBoot.web.model.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();

    public boolean addUser(User user);

    public void removeUser(long id);

    public boolean updateUser(String name, String password, Long id);

    public List<User> getUserById(long id);

    public void addAdminAndUserPanel();
}
