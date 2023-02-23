package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE USERS " +
                "(id BIGINT not NULL AUTO_INCREMENT, " +
                " name VARCHAR(255), " +
                " lastName VARCHAR(255), " +
                " age TINYINT, " +
                " PRIMARY KEY ( id ))";

        try (Connection connection = Util.setConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Table has been created!");

        } catch (SQLException ignored) {
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS USERS";

        try (Connection connection = Util.setConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Table has been deleted!");
        } catch (SQLException ignored) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO USERS (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.setConnection();
             PreparedStatement preparedStatement= connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.println("New user has been successfully added!");
        } catch (SQLException e) {
            System.out.println("Failed to add new user.");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM USERS WHERE id = ?";
        try (Connection connection = Util.setConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User has been successfully removed");
        } catch (SQLException e) {
            System.out.println("Failed to remove user by id");
        }
    }

    public List<User> getAllUsers() {
        var list = new ArrayList<User>();
        String query = "SELECT * FROM USERS";

        try (Connection connection = Util.setConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(new User(resultSet.getString(2),
                        resultSet.getString(3), resultSet.getByte(4)));
            }

        } catch (SQLException e) {
            System.out.println("Failed to get users list");
        }
        return list;
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE USERS";

        try (Connection connection = Util.setConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Table has been cleared!");
        } catch (SQLException e) {
            System.out.println("Failed to clear table");
        }
    }
}
