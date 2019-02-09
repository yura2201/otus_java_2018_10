package ru.otus.java.dbservice;

import java.sql.ResultSet;
import java.util.List;

public interface ResultSetExtractor<T> {

  T extractData(ResultSet rs, List<String> fieldAliases, Class<T> clazz);

  List<T> extractListData(ResultSet rs, List<String> fieldAliases, Class<T> clazz);
}
