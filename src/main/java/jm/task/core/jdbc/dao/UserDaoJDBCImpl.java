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
        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE users (id INTEGER NOT NULL AUTO_INCREMENT, name VARCHAR(45), lastname VARCHAR(45), age TINYINT(3), PRIMARY KEY (id))");
        } catch (SQLException e) {
            System.out.println("SQL error occured 1");
        }
    }

    public void dropUsersTable() {
        try (Connection conn = Util.getConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate("DROP TABLE if exists users");
        } catch (SQLException e) {
            System.out.println("SQL error occured 2");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = Util.getConnection();
             PreparedStatement preStmt = conn.prepareStatement("INSERT INTO users VALUES (default, ?, ?, ?)")) {
            preStmt.setString(1, name);
            preStmt.setString(2, lastName);
            preStmt.setByte(3, age);
            preStmt.execute();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("SQL error occured 3");
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getConnection();
             PreparedStatement preStmt = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
            preStmt.setLong(1, id);
            preStmt.execute();
        } catch (SQLException e) {
            System.out.println("SQL error occured 4");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String order = "select * from  users";
        try (Statement statement = Util.getConnection().createStatement(); ResultSet resultSet = statement.executeQuery(order)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("SQL error occured 5");
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection conn = Util.getConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            System.out.println("SQL error occured 6");
        }
    }
}


