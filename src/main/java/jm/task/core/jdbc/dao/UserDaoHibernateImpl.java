package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS USERS\n" +
                    "(\n" +
                    "    id       BIGINT AUTO_INCREMENT\n" +
                    "        PRIMARY KEY ,\n" +
                    "    name     VARCHAR(255) NULL,\n" +
                    "    lastName VARCHAR(255) NULL,\n" +
                    "    age      TINYINT      NULL\n" +
                    ")").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS USERS").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем " + name + " был успешно добавлен в таблицу" );
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save user");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to remove user");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;

        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            TypedQuery<User> typedQuery = session.createQuery("FROM User", User.class);
            users = typedQuery.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE USERS").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
