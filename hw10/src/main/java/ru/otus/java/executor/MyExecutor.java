package ru.otus.java.executor;

import java.sql.SQLException;
import java.util.List;

import ru.otus.java.dbservice.ResultSetExtractor;

public interface MyExecutor<T> {

  void save(T objectData) throws SQLException;

  T load(Object id, Class<T> clazz, ResultSetExtractor<T> extractor) throws SQLException;

  List<T> loadAll(Class<T> clazz, ResultSetExtractor<T> extractor) throws SQLException;

  void update(String sql, Object... args) throws SQLException;
}
