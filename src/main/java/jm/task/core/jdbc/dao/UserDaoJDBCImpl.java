package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public void createUsersTable() {
        try (Connection connection = Util.getMySQLConnection();) {
            Statement statement = connection.createStatement();
            String createTableSQLQuery = "CREATE TABLE IF NOT EXISTS users (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(45),`last_name` VARCHAR(45),`age` INT, PRIMARY KEY (`id`))";
            statement.execute(createTableSQLQuery);
        } catch (ClassNotFoundException | SQLException ignored) {
        }


    }

    public void dropUsersTable() {
        try (Connection connection = Util.getMySQLConnection();) {
            Statement statement = connection.createStatement();
            String dropTableSQLQuery = "DROP TABLE IF EXISTS users";
            statement.execute(dropTableSQLQuery);
        } catch (ClassNotFoundException | SQLException ignored) {
        }
    }

    public void saveUser(String name, String lastName, byte age)  {
        String saveUserQuery;
        PreparedStatement preparedStatement;
        try (Connection connection = Util.getMySQLConnection()) {
            saveUserQuery = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(saveUserQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
            System.out.printf("User с именем %s добавлен в базу данных\n", name);
        } catch (ClassNotFoundException | SQLException ignored) {
        }
    }

    public void removeUserById(long id)  {
        try (Connection connection = Util.getMySQLConnection()) {
            String removeUserByIDQuery = "DELETE FROM Users WHERE ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(removeUserByIDQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.execute(removeUserByIDQuery);
        } catch (ClassNotFoundException | SQLException ignored) {
        }
    }

    public List<User> getAllUsers()  {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getMySQLConnection()){
            String sql = "Select ID, NAME, LAST_NAME, AGE from Users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery(sql);
            while (rs.next()){
                User user = new User();
                user.setId((long) rs.getInt(1));
                user.setName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setAge((byte) rs.getInt(4));
                userList.add(user);
            }
        } catch (ClassNotFoundException | SQLException ignored) {
        }
        return userList;
    }

    public void cleanUsersTable()  {
        try (Connection connection = Util.getMySQLConnection()) {
            Statement statement = connection.createStatement();
            String truncateQuery = "TRUNCATE TABLE users";
            statement.execute(truncateQuery);
        } catch (ClassNotFoundException | SQLException ignored) {
        }
    }
}
