package JDBC;

import com.tms.JPA.User;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepository {
    private Session session = null;
    CriteriaBuilder criteriaBuilder = null;

    public UserRepository() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        session = factory.openSession();
        criteriaBuilder = session.getCriteriaBuilder();
    }

    public List<User> findAll() {
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        return session.createQuery(criteria).getResultList();
    }

    public User findUserById(Long id) {
        try {
            CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.select(root).where(criteriaBuilder.equal(root.get("id"), id));

            return session.createQuery(criteria).getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean createUser(User user) {
        try {
            session.getTransaction().begin();
            session.persist(user);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e);
        }
        return false;
    }

    public boolean updateUser(User user) { //лучше описать методы по апдейту каждого нужного поля отдельно
        try {
            CriteriaUpdate<User> criteria = criteriaBuilder.createCriteriaUpdate(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.set("username", user.getUsername());

            criteria.where(criteriaBuilder.equal(root.get("id"), user.getId()));

            session.getTransaction().begin();
            session.createMutationQuery(criteria).executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e);
        }
        return false;
    }

    public boolean updateUserPassword(String newPassword, Long id) {
        try {
            User user = session.get(User.class, id);
            user.setUserPassword(newPassword);
            session.getTransaction().begin();
            session.merge(user); //update
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e);
        }
        return false;
    }

    public boolean deleteUser(Long id) {
        try {
            CriteriaDelete<User> criteria = criteriaBuilder.createCriteriaDelete(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.where(criteriaBuilder.equal(root.get("id"), id));

            session.getTransaction().begin();
            session.createMutationQuery(criteria).executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e);
        }
        return false;
    }
}
