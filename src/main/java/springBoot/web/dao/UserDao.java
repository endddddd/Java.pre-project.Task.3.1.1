package springBoot.web.dao;


import springBoot.web.model.User;

import java.util.List;

public interface UserDao  {

    public List<User> getAllUsers();

    public void addUser(User user);

    public void removeUser(long id);

    public void updateUser(String name, String password, Long id);

    public boolean isNotReg(String name);

    public List<User> getUserById(long id);
    public User getUserByName(String name);

}

