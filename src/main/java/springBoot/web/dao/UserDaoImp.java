package springBoot.web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import springBoot.web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void removeUser(long id) {

        entityManager.remove(entityManager.find(User.class, id));

    }

    @Override
    public void updateUser(String name, String password, Long id) {

        entityManager
                .createQuery("UPDATE User SET name =:pName, password =:pPass WHERE id =:pId")
                .setParameter("pName", name)
                .setParameter("pPass", password)
                .setParameter("pId", id)
                .executeUpdate();
    }

    // нельзя добавить пользователей с одинаковым именем
    @Override
    public boolean isNotReg(String name) {
        return getAllUsers()
                .stream()
                .anyMatch((e) -> e.getUsername().hashCode() == name.hashCode());
    }

    @Override
    public List<User> getUserById(long id) {
        return entityManager.createQuery("From User where id =:pId")
                .setParameter("pId", id).getResultList();
    }

    @Override
    public User getUserByName(String name) {
        return (User) entityManager.createQuery("From User where name =:pName")
                .setParameter("pName", name).getSingleResult();
    }

}
