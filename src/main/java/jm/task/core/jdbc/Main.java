package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userService = new UserDaoHibernateImpl();
        userService.createUsersTable();
        userService.saveUser("James", "Clement", (byte) 39);
        userService.saveUser("Nick", "Mason", (byte) 43);
        userService.saveUser("Claire", "Tonti", (byte) 39);
        userService.saveUser("Todd", "Howard", (byte) 51);
        System.out.println();
        System.out.print(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
