package JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcExample {
    private final static String SELECT_ALL = "SELECT * FROM users ";
    private final static String SELECT_USER_FROM_USERS = "SELECT * FROM users WHERE id=?";
    private final static String INSERT_USER_INTO_USERS = "INSERT INTO users(id, username, user_password, created, changed, age)" +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?)";

    private final static String UPDATE_USER_INTO_USERS = "UPDATE users SET username=?, user_password=?," +
            " changed=?, age=? WHERE id =?";
    private final static String REMOVE_USER_FROM_USERS = "DELETE FROM users WHERE id=?";
    private final static String OLDEST_USER = "{? = call max_old_in_the_users()}";
    private final static String TRUNCATE_TELEPHONE = "CALL truncate_telephone_table()";
    private Connection connection = null;

    public JdbcExample() {
        try {
            //1.регистрация драйвера
            Class.forName("org.postgresql.Driver");
            //2. Создаем соединение (URL, login, password)
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/c71_database", "postgres", "123");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            //3. Описать Statement, PrepareStatement, CallableStatement
            Statement statement = connection.createStatement();
           /* statement.execute(""); //отправит и вернет boolean
            statement.executeUpdate(""); // CREATE UPDATE DELETE*//*возвращает сколько строк было изменено
            statement.executeQuery(""); // - отправит и вернет ответ*/
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            //4. Парсинг ResultSet
            while (resultSet.next()) {
                /*System.out.println("id: " + resultSet.getLong("id") +
                        "\nusername: " + resultSet.getString("username")
                        + "\nuser_password: " + resultSet.getString("user_password")
                        + "\ncreated: " + resultSet.getTimestamp("created")
                        + "\nchanged: " + resultSet.getTimestamp("changed")
                        + "\nage: " + resultSet.getInt("age"));
                System.out.println();*/
                User user = parseUser(resultSet);
                if (user.getId() != null) {
                    users.add(parseUser(resultSet));
                }
                System.out.println(parseUser(resultSet));
            }
            //connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return users;
    }

    public User getUser(Long id) {
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_FROM_USERS);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            //4. Парсинг ResultSet
            while (resultSet.next()) {
                /*System.out.println("id: " + resultSet.getLong("id") +
                        "\nusername: " + resultSet.getString("username")
                        + "\nuser_password: " + resultSet.getString("user_password")
                        + "\ncreated: " + resultSet.getTimestamp("created")
                        + "\nchanged: " + resultSet.getTimestamp("changed")
                        + "\nage: " + resultSet.getInt("age"));
                System.out.println();*/
                user = parseUser(resultSet);
                if (user.getId() != null) {
                    return user;
                }
                System.out.println(parseUser(resultSet));
            }
            //connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return user;
    }

    public boolean createUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_INTO_USERS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getUser_password());
            statement.setTimestamp(3, user.getCreated());
            statement.setTimestamp(4, user.getChanged());
            statement.setInt(5, user.getAge());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean updateUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER_INTO_USERS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getUser_password());
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.setInt(4, user.getAge());
            statement.setLong(5, user.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean deleteUser(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(REMOVE_USER_FROM_USERS);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public User parseUser(ResultSet resultSet) {
        User user = new User();
        try {
            user.setId(resultSet.getLong("id"));
            user.setUser_password(resultSet.getString("user_password"));
            user.setUsername(resultSet.getString("username"));
            user.setCreated(resultSet.getTimestamp("created"));
            user.setChanged(resultSet.getTimestamp("changed"));
            user.setAge(resultSet.getInt("age"));
        } catch (SQLException e) {
            System.out.println(e);
        }
        return user;
    }

    public boolean checkTransaction() {
        try {
            connection.setAutoCommit(false);
            PreparedStatement statementAge = connection.prepareStatement("UPDATE users SET age=105 where id=11");
            PreparedStatement statementUsername = connection.prepareStatement("UPDATE users SET username='USER_TRANSACTION' where id=11");
            PreparedStatement statementPassword = connection.prepareStatement("UPDATE users SET user_password='USER_PASS' where id=11");
            statementAge.executeUpdate();
            statementPassword.executeUpdate();
            statementUsername.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);//потому что коннект общий для всех
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            try {
                connection.rollback();
            } catch (SQLException exception) {
                System.out.println(exception);
            }
        }
        return false;
    }

    public String getUsernameFromTheOldestUser() {
        try {
            CallableStatement statement = connection.prepareCall(OLDEST_USER);
            statement.registerOutParameter(1, Types.VARCHAR);
            statement.executeUpdate();
            return statement.getString(1);
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public boolean truncateTelephoneTable() {
        try {
            CallableStatement statement = connection.prepareCall(TRUNCATE_TELEPHONE);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public void connectionClose() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
