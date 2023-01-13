package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory;
    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                            "(`id` INT NOT NULL AUTO_INCREMENT," +
                            "`name` VARCHAR(45)," +
                            "`last_name` VARCHAR(45)," +
                            "`age` INT, PRIMARY KEY (`id`))")
                    .addEntity(User.class)
                    .executeUpdate();
            transaction.commit();
            System.out.println("Successful table creation.");
        } catch (Exception e) {
            System.out.println("Table was not created.");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS  users").executeUpdate();
            transaction.commit();
            System.out.print("\nTable successfully dropped");
        } catch (Exception e) {
            System.out.print("\nTable drop failure.");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User newUser = new User(name, lastName, age);
            session.save(newUser);
            session.getTransaction().commit();
            System.out.printf("\nUser %s successfully saved.", newUser.getName());
        } catch (Exception e) {
            System.out.print("\nUser saving failure.");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.print("\nUser successfully removed.");
        } catch (Exception e) {
            System.out.print("\nRemoval error.");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from User");
            userList = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            List<User> userList = getAllUsers();
            transaction = session.beginTransaction();
            for (User user : userList) {
                session.delete(user);
                System.out.printf("\nDeleting %s", user.getName());
            }
            session.getTransaction().commit();
            System.out.println("\nTable successfully truncated");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
