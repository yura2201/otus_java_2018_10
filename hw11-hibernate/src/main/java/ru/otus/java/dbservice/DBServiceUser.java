package ru.otus.java.dbservice;

import java.util.Optional;

import ru.otus.java.database.entity.User;

public interface DBServiceUser {

  long saveUser(User user);

  Optional<User> getUser(long id);

}
