package JDBC;

import com.tms.JPA.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;

public class UserRepository {
    private EntityManager entityManager = null;

    public UserRepository() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        entityManager = factory.createEntityManager();
    }

    public List<User> findAll() {
       // Query query = entityManager.createNativeQuery("SELECT * FROM users", User.class);// без  User.class не будет toString()
        Query query = entityManager.createQuery("SELECT users", User.class); //JPQL
        return query.getResultList();
    }

    public User findUserById(Long id) {
        try {
            return entityManager.find(User.class, id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean createUser(User user) {
        try {
            entityManager.getTransaction().begin(); //начинаем транзакцию
            entityManager.persist(user); //метод для сохранения
            entityManager.getTransaction().commit();//коммиты
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println(e);
        }
        return false;
    }

    public boolean updateUser(User user) { //лучше описать методы по апдейту каждого нужного поля отдельно
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(user); //update
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println(e);
        }
        return false;
    }

    public boolean updateUserPassword(String newPassword, Long id) {
        try {
            User user = entityManager.find(User.class, id);
            user.setUserPassword(newPassword);
            entityManager.getTransaction().begin();
            entityManager.merge(user); //update
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println(e);
        }
        return false;
    }

    public boolean deleteUser(Long id) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(User.class, id));
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println(e);
        }
        return false;
    }
}
