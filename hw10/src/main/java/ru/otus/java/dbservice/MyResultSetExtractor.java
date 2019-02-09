package ru.otus.java.dbservice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.java.exception.DataAccessException;
import ru.otus.java.exception.InconsistentResultException;
import ru.otus.java.util.ReflectionHelper;

public class MyResultSetExtractor<T> implements ResultSetExtractor<T> {

  private static final Logger LOG = LoggerFactory.getLogger(MyResultSetExtractor.class);

  public MyResultSetExtractor() {
    BasicConfigurator.configure();
  }

  @Override
  public T extractData(ResultSet rs, List<String> fieldAliases, Class<T> clazz)
      throws SQLException {
    T object;
    try {
      object = clazz.newInstance();
      rs.next();
      for (int i = 0; i < fieldAliases.size(); i++) {
        ReflectionHelper.setFieldValue(object, fieldAliases.get(i), getResultSetValue(rs, i + 1));
      }
      if (rs.next()) {
        throw new InconsistentResultException();
      }
      return object;
    } catch (InstantiationException | IllegalAccessException e) {
      LOG.error("extractData - {}", e.getMessage(), e);
      throw new DataAccessException(e.getMessage());
    }
  }

  @Override
  public List<T> extractListData(ResultSet rs, List<String> fieldAliases, Class<T> clazz)
      throws SQLException {
    List<T> result = new ArrayList<>();
    T object;
    try {
      while (rs.next()) {
        object = clazz.newInstance();
        for (int i = 0; i < fieldAliases.size(); i++) {
          ReflectionHelper.setFieldValue(object, fieldAliases.get(i), getResultSetValue(rs, i + 1));
        }
        result.add(object);
      }
    } catch (InstantiationException | IllegalAccessException e) {
      LOG.error("extractListData - {}", e.getMessage(), e);
      throw new DataAccessException(e.getMessage());
    }
    return result;
  }

  private static Object getResultSetValue(ResultSet rs, int index) throws SQLException {
    Object obj = rs.getObject(index);
    if (obj instanceof java.sql.Date) {
      if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
        obj = rs.getTimestamp(index);
      }
    }
    return obj;
  }
}
