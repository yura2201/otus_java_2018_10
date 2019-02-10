package ru.otus.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.otus.java.database.entity.User;
import ru.otus.java.dbservice.DataSourceH2;
import ru.otus.java.dbservice.MyResultSetExtractor;
import ru.otus.java.executor.MyExecutor;
import ru.otus.java.executor.MyExecutorImpl;

public class Work10Test {

  private static final DataSource ds = new DataSourceH2();

  @BeforeClass
  public static void init() throws SQLException {
    try (Connection connection = ds.getConnection();
        PreparedStatement pst = connection.prepareStatement(
            "create table user (id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
      pst.executeUpdate();
    }
    System.out.println("table created");
  }

  @Test
  public void testLoadSuccess() throws Exception {
    MyExecutor<User> executor = new MyExecutorImpl<>(ds.getConnection());
    String name = "TEST_NAME_01";
    int age = 101;
    executor.update("insert into user(name, age) values (?, ?)", name, age);

    User result = getUserFromList(executor, name, age);

    User user = executor.load(result.getId(), User.class, new MyResultSetExtractor<>());

    Assert.assertNotNull("Non null object expected", user);
    Assert.assertEquals("Expected name=[" + name + "] but actual=[" + user.getName() + "]", name,
        user.getName());
    Assert.assertTrue("Expected age=[" + age + "] but actual=[" + user.getAge() + "]",
        age == user.getAge());
  }

  @Test
  public void testInsertSuccess() throws Exception {
    User user = new User();
    String name = "TEST_NAME_02";
    int age = 102;
    user.setAge(age);
    user.setName(name);
    MyExecutor<User> executor = new MyExecutorImpl<>(ds.getConnection());
    executor.save(user);
    List<User> users = executor.loadAll(User.class, new MyResultSetExtractor<>());
    Assert.assertNotNull("Non null object expected", users);
    Assert.assertFalse("Expected not empty list but it is", users.isEmpty());

    Assert.assertTrue("Expected user=[" + user + "] but not found", users.stream().anyMatch(
        item -> Objects.equals(name, item.getName()) && Objects.equals(age, item.getAge())));
  }

  @Test
  public void testUpdateSuccess() throws Exception {
    MyExecutor<User> executor = new MyExecutorImpl<>(ds.getConnection());
    String name = "TEST_NAME_03";
    int age = 103;
    executor.update("insert into user(name, age) values (?, ?)", name, age);

    User result = getUserFromList(executor, name, age);

    String updName = "TEST_NAME_04";
    int updAge = 104;
    result.setAge(updAge);
    result.setName(updName);
    executor.save(result);

    User user = executor.load(result.getId(), User.class, new MyResultSetExtractor<>());
    Assert.assertNotNull("Non null object expected", user);
    Assert.assertEquals("Expected name=[" + updName + "] but actual=[" + user.getName() + "]",
        updName, user.getName());
    Assert.assertTrue("Expected age=[" + updAge + "] but actual=[" + user.getAge() + "]",
        updAge == user.getAge());
  }

  private User getUserFromList(MyExecutor<User> executor, String name, int age)
      throws InstantiationException, IllegalAccessException, SQLException {
    List<User> users = executor.loadAll(User.class, new MyResultSetExtractor<>());
    Assert.assertNotNull("Non null object expected", users);
    Assert.assertFalse("Expected not empty list but it is", users.isEmpty());

    User result = users.stream()
        .filter(item -> Objects.equals(name, item.getName()) && Objects.equals(age, item.getAge()))
        .findAny().orElse(null);
    Assert.assertNotNull("Expected user not found", result);

    return result;
  }
}
