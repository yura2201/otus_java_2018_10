package ru.otus.java.dbservice;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import ru.otus.java.database.entity.Address;
import ru.otus.java.database.entity.Phone;
import ru.otus.java.database.entity.User;

public class DbServiceUserImpl implements DBServiceUser {

  private final SessionFactory sessionFactory;

  public DbServiceUserImpl() {
    Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

    StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
        .applySettings(configuration.getProperties()).build();

    Metadata metadata = new MetadataSources(serviceRegistry).addAnnotatedClass(User.class)
        .addAnnotatedClass(Phone.class).addAnnotatedClass(Address.class).getMetadataBuilder()
        .build();

    sessionFactory = metadata.getSessionFactoryBuilder().build();
  }

  @Override
  public long saveUser(User user) {
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.persist(user);
      session.getTransaction().commit();
      return user.getId();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex);
    }
  }

  @Override
  public Optional<User> getUser(long id) {
    try (Session session = sessionFactory.openSession()) {
      Optional<User> opt = Optional.ofNullable(session.get(User.class, id));
      return opt;
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex);
    }
  }
}
