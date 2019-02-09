package ru.otus.java.executor;

import java.sql.SQLException;
import java.util.List;

public interface MyExecutor<T> {

  void save(T objectData) throws SQLException;

  T load(Object id, Class<T> clazz) throws SQLException;

  List<T> loadAll(Class<T> clazz) throws SQLException;

  void update(String sql, Object... args) throws SQLException;
}
