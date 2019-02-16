package ru.otus.java;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.java.database.entity.Address;
import ru.otus.java.database.entity.Phone;
import ru.otus.java.database.entity.User;
import ru.otus.java.dbservice.DBServiceUser;
import ru.otus.java.dbservice.DbServiceUserImpl;

/**
 * Created by yurait6@gmail.com on 15.11.2018.
 */
public class Work11 {

  private static final Logger LOG = LoggerFactory.getLogger(Work11.class);

  public static void main(String[] args) throws InterruptedException {
    BasicConfigurator.configure();

    DBServiceUser service = new DbServiceUserImpl();
    User user = new User();
    Address address = new Address();
    address.setStreet("Fleet street, 1");
    user.setAddress(address);

    Set<Phone> phones = new HashSet<>(Arrays.asList(new Phone("12345"), new Phone("67890")));
    user.setPhones(phones);

    Long userId = service.saveUser(user);
    LOG.debug("userId=[{}]", userId);

    User user1 = service.getUser(userId).get();
    LOG.debug("user={}", user1);
  }
}
