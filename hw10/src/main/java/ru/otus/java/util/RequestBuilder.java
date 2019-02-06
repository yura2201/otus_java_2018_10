package ru.otus.java.util;

import java.util.List;
import java.util.Map;

public interface RequestBuilder<T> {

  boolean doCheckRequest(T object);

  Map.Entry<String, Object> buildCheckEntityExistenceRequest(T object);

  Map.Entry<String, List<Object>> buildUpdateRequest(T object);

  Map.Entry<String, List<Object>> buildInsertRequest(T object);

  Map.Entry<String, List<String>> buildLoadRequest(Class<T> clazz);

  Map.Entry<String, List<String>> buildLoadAllRequest(Class<T> clazz);
}
