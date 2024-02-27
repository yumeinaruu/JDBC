package JDBC;

import com.tms.JPA.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private Session session = null;

    public UserRepository() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        session = factory.openSession();
    }

    public List<User> findAll() {
        try {
            return session.createQuery("from users", User.class).getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    public User findUserById(Long id) {
        try {
            Query<User> query = session.createQuery("from users u where u.id = :userId", User.class);
            query.setParameter("userId", id);
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean createUser(User user) {
        try {
            //Query<User> query = session.createQuery("insert into users (username, userPassword, created, changed, age) select (username, userPassword, created, changed, age) from second_table_name", User.class);
            //int result = query.executeUpdate(); //еще добавить транзакции
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
            Query<User> query = session.createQuery("update users" +
                    " set username=:un, userPassword=:up, created=:cr, changed=:ch, age=:a where id=:id", User.class);
            query.setParameter("un", user.getUsername());
            query.setParameter("up", user.getUserPassword());
            query.setParameter("cr", user.getCreated());
            query.setParameter("ch", user.getChanged());
            query.setParameter("a", user.getAge());
            query.setParameter("id", user.getId());

            query.executeUpdate();//и транзакции добавить
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
            Query<User> query = session.createQuery("delete from users where id =:id", User.class);
            query.setParameter("id", id);

            query.executeUpdate();//транзакции еще
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e);
        }
        return false;
    }
}
