package JDBC;

import com.tms.JPA.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();

/*        User user = new User();
        user.setAge(13);
        user.setUsername("Vova");
        user.setUserPassword("qwerty");
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setChanged(Timestamp.valueOf(LocalDateTime.now()));*/

        //System.out.println(userRepository.createUser(user));

        //System.out.println(userRepository.deleteUser(10L));

  /*      User user = userRepository.findUserById(17L);
        System.out.println(user);*/

        /*user.setUserPassword("qwerty_updated");
        System.out.println(userRepository.updateUser(user));*/

        System.out.println(userRepository.findAll());
    }
}
