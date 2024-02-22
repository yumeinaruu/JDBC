package JDBC;

import java.sql.Timestamp;

public class Main {
    public static void main(String[] args) {
        JdbcExample example = new JdbcExample();
        User user = new User(14L, "jdbc_example",
                "123", new Timestamp(1L),
                new Timestamp(2L), 13);
        example.findAll();
        System.out.println(example.getUser(13L));
        System.out.println(example.deleteUser(13L));
        System.out.println(example.checkTransaction());
        System.out.println(example.getUsernameFromTheOldestUser());
        System.out.println(example.truncateTelephoneTable());
        example.connectionClose();
    }
}
