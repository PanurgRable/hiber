package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/mydbTest";
    private static final String USER = "root";
    private static final String PASSWORD = "root000000!";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";


    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.url", URL)
                .setProperty("hibernate.connection.username", USER)
                .setProperty("hibernate.connection.password", PASSWORD)
                .setProperty("hibernate.connection.driver_class", DRIVER)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .addAnnotatedClass(User.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    public static Connection getMySQLConnection() throws SQLException,
            ClassNotFoundException {
        String hostName = "localhost";
        String dbName = "firstdb";
        String userName = "root";
        String password = "admin";
        return getMySQLConnection(hostName, dbName, userName, password);
    }
    public static Connection getMySQLConnection (String hostName, String dbName,
            String userName, String password) throws SQLException {
        String connectionURL = String.format("jdbc:mysql://%s:3306/%s", hostName, dbName);
        return DriverManager.getConnection(connectionURL, userName,
                password);
    }

}
