package ru.otus.java.executor;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.java.InconsistentResultException;
import ru.otus.java.util.ReflectionHelper;
import ru.otus.java.util.RequestBuilder;
import ru.otus.java.util.RequestBuilderImpl;

public class MyExecutorImpl<T> implements MyExecutor<T> {

  private static final Logger LOG = LoggerFactory.getLogger(MyExecutorImpl.class);

  private final Connection connection;
  private final RequestBuilder<T> requestBuilder;

  public MyExecutorImpl(Connection connection) {
    this.connection = connection;
    this.requestBuilder = new RequestBuilderImpl<>();
  }

  @Override
  public void save(T objectData) throws SQLException {
    Savepoint savePoint = this.connection.setSavepoint("savePointSave");

    Map.Entry<String, List<Object>> requestData;
    if (doInsert(objectData)) {
      requestData = requestBuilder.buildInsertRequest(objectData);
    } else {
      requestData = requestBuilder.buildUpdateRequest(objectData);
    }
    try {
      executeUpdate(requestData.getKey(), requestData.getValue().toArray());
    } catch (SQLException e) {
      this.connection.rollback(savePoint);
      LOG.error("save - {}", e.getMessage(), e);
      throw e;
    }
  }

  private void executeUpdate(String sql, Object... args) throws SQLException {
    try (PreparedStatement pst = connection.prepareStatement(sql)) {
      if (Objects.nonNull(args)) {
        for (int i = 0; i < args.length; i++) {
          setParameterValue(pst, i + 1, args[i]);
        }
      }
      pst.executeUpdate();
    }
  }

  private boolean doInsert(T object) throws SQLException {
    if (requestBuilder.doCheckRequest(object)) {
      Map.Entry<String, Object> checkRequestData = requestBuilder
          .buildCheckEntityExistenceRequest(object);
      try (PreparedStatement pst = connection.prepareStatement(checkRequestData.getKey())) {
        setParameterValue(pst, 1, checkRequestData.getValue());
        try (ResultSet rs = pst.executeQuery()) {
          rs.next();
          return rs.getInt(1) == 0;
        }
      }
    }
    return true;
  }

  @Override
  public T load(Object id, Class<T> clazz)
      throws SQLException, InstantiationException, IllegalAccessException {
    Map.Entry<String, List<String>> requestData = requestBuilder.buildLoadRequest(clazz);
    try (PreparedStatement pst = connection.prepareStatement(requestData.getKey())) {
      setParameterValue(pst, 1, id);
      try (ResultSet rs = pst.executeQuery()) {
        List<String> fieldNames = requestData.getValue();
        T object = clazz.newInstance();
        rs.next();
        for (int i = 0; i < fieldNames.size(); i++) {
          ReflectionHelper.setFieldValue(object, fieldNames.get(i), getResultSetValue(rs, i + 1));
        }
        if (rs.next()) {
          throw new InconsistentResultException();
        }
        return object;
      }
    }
  }

  @Override
  public List<T> loadAll(Class<T> clazz)
      throws SQLException, InstantiationException, IllegalAccessException {
    Map.Entry<String, List<String>> requestData = requestBuilder.buildLoadAllRequest(clazz);
    try (PreparedStatement pst = connection.prepareStatement(requestData.getKey())) {
      try (ResultSet rs = pst.executeQuery()) {
        List<String> fieldNames = requestData.getValue();
        List<T> result = new ArrayList<>();
        T object;
        while (rs.next()) {
          object = clazz.newInstance();
          for (int i = 0; i < fieldNames.size(); i++) {
            ReflectionHelper.setFieldValue(object, fieldNames.get(i), getResultSetValue(rs, i + 1));
          }
          result.add(object);
        }
        return result;
      }
    }
  }

  @Override
  public void update(String sql, Object... args) throws SQLException {
    Savepoint savePoint = this.connection.setSavepoint("savePointUpdate");
    try {
      executeUpdate(sql, args);
    } catch (SQLException e) {
      this.connection.rollback(savePoint);
      LOG.error("update - {}", e.getMessage(), e);
      throw e;
    }
  }

  private void setParameterValue(PreparedStatement ps, int index, Object value)
      throws SQLException {
    if (isStringValue(value.getClass())) {
      ps.setString(index, value.toString());
    } else if (isDateValue(value.getClass())) {
      ps.setTimestamp(index, new java.sql.Timestamp(((java.util.Date) value).getTime()));
    } else if (value instanceof Calendar) {
      Calendar cal = (Calendar) value;
      ps.setTimestamp(index, new java.sql.Timestamp(cal.getTime().getTime()), cal);
    } else {
      ps.setObject(index, value);
    }
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

  private static boolean isStringValue(Class<?> inValueType) {
    return (CharSequence.class.isAssignableFrom(inValueType)
        || StringWriter.class.isAssignableFrom(inValueType));
  }

  private static boolean isDateValue(Class<?> inValueType) {
    return (java.util.Date.class.isAssignableFrom(inValueType)
        && !(java.sql.Date.class.isAssignableFrom(inValueType)
            || java.sql.Time.class.isAssignableFrom(inValueType)
            || java.sql.Timestamp.class.isAssignableFrom(inValueType)));
  }
}
