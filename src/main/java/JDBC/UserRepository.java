package JDBC;

import com.tms.JPA.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class UserRepository {
    private EntityManager entityManager = null;

    public UserRepository() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        entityManager = factory.createEntityManager();
    }
  /*  public List<User> findAll() {
    }*/

   /* public User getUser(Long id) {
    }*/

    public boolean createUser(User user) {
        try{
            entityManager.getTransaction().begin(); //начинаем транзакцию
            entityManager.persist(user);
            entityManager.getTransaction().commit();//коммиты
            return true;
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e);
        }
        return false;
    }

   /* public boolean updateUser(User user) {
    }*/

  /*  public boolean deleteUser(Long id) {
    }*/
}
