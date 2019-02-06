package ru.otus.java.util;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.otus.java.annotation.Id;

public class RequestBuilderImpl<T> implements RequestBuilder<T> {

  @Override
  public boolean doCheckRequest(T object) {
    Field idField = ReflectionHelper.getAnnotatedField(object.getClass(), Id.class);
    return Objects.nonNull(ReflectionHelper.getFieldValue(object, idField.getName()));
  }

  @Override
  public Map.Entry<String, Object> buildCheckEntityExistenceRequest(T object) {
    Field idField = ReflectionHelper.getAnnotatedField(object.getClass(), Id.class);
    StringBuilder sb = new StringBuilder("SELECT count(1) FROM ")
        .append(getDbName(object.getClass().getSimpleName())).append(" WHERE ")
        .append(getDbName(idField.getName())).append(" = ?");
    return new AbstractMap.SimpleEntry<>(sb.toString(),
        ReflectionHelper.getFieldValue(object, idField.getName()));
  }

  @Override
  public Map.Entry<String, List<Object>> buildUpdateRequest(T object) {
    Field idField = ReflectionHelper.getAnnotatedField(object.getClass(), Id.class);
    StringBuilder sb = new StringBuilder("UPDATE ")
        .append(getDbName(object.getClass().getSimpleName())).append("\nSET ");
    List<Object> params = new ArrayList<>();
    for (Field field : object.getClass().getDeclaredFields()) {
      if (!Objects.equals(idField, field)) {
        sb.append(getDbName(field.getName())).append(" = ?,").append("\n");
        params.add(ReflectionHelper.getFieldValue(object, field.getName()));
      }
    }
    sb.deleteCharAt(sb.lastIndexOf(","));
    sb.append("WHERE ").append(getDbName(idField.getName())).append(" = ?");
    params.add(ReflectionHelper.getFieldValue(object, idField.getName()));
    return new AbstractMap.SimpleEntry<>(sb.toString(), params);
  }

  @Override
  public Map.Entry<String, List<Object>> buildInsertRequest(T object) {
    Field idField = ReflectionHelper.getAnnotatedField(object.getClass(), Id.class);
    StringBuilder sb = new StringBuilder("INSERT INTO ")
        .append(getDbName(object.getClass().getSimpleName())).append(" (");
    List<Object> params = new ArrayList<>();
    StringBuilder values = new StringBuilder("\nVALUES(");
    for (Field field : object.getClass().getDeclaredFields()) {
      if (!Objects.equals(idField, field)) {
        sb.append(getDbName(field.getName())).append(",");
        values.append("?,");
        params.add(ReflectionHelper.getFieldValue(object, field.getName()));
      }
    }
    sb.deleteCharAt(sb.lastIndexOf(","));
    values.deleteCharAt(values.lastIndexOf(","));
    values.append(")");
    sb.append(")").append(values.toString());
    return new AbstractMap.SimpleEntry<>(sb.toString(), params);
  }

  private String getDbName(String name) {
    final StringBuilder sb = new StringBuilder();
    sb.append(Character.toLowerCase(name.charAt(0)));
    for (char ch : name.substring(1, name.length()).toCharArray()) {
      if (Character.isUpperCase(ch)) {
        sb.append("_").append(Character.toLowerCase(ch));
      } else {
        sb.append(Character.toLowerCase(ch));
      }
    }
    return sb.toString();
  }

  @Override
  public Map.Entry<String, List<String>> buildLoadRequest(Class<T> clazz) {
    Field idField = ReflectionHelper.getAnnotatedField(clazz, Id.class);
    Map.Entry<String, List<String>> loadAllRequestData = buildLoadAllRequest(clazz);
    StringBuilder sb = new StringBuilder(loadAllRequestData.getKey());
    String loadRequest = sb.append(" WHERE ").append(getDbName(idField.getName())).append(" = ?")
        .toString();
    return new AbstractMap.SimpleEntry<>(loadRequest, loadAllRequestData.getValue());
  }

  @Override
  public Map.Entry<String, List<String>> buildLoadAllRequest(Class<T> clazz) {
    StringBuilder sb = new StringBuilder("SELECT ");
    final List<String> dbFieldAliases = new ArrayList<>();
    for (Field field : clazz.getDeclaredFields()) {
      dbFieldAliases.add(field.getName());
      sb.append(getDbName(field.getName())).append(" as ").append(field.getName()).append(",");
    }
    sb.deleteCharAt(sb.lastIndexOf(","));
    return new AbstractMap.SimpleEntry<>(
        sb.append(" FROM ").append(getDbName(clazz.getSimpleName())).toString(), dbFieldAliases);
  }
}
