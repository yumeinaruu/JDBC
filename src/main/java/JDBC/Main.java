package JDBC;

import com.tms.JPA.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        User user = new User();
        user.setAge(13);
        user.setUsername("Vova");
        user.setUserPassword("qwerty");
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        System.out.println(userRepository.createUser(user));
    }
}
