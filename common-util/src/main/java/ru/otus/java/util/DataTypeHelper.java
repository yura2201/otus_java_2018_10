package ru.otus.java.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTypeHelper {

  private DataTypeHelper() {
  }

  private static final String DATE_ISO8601_FORMAT = "yyyy-MM-dd";

  public static String dateToString(Date date) {
    if (date != null) {
      if (date instanceof java.sql.Date) {
        return new SimpleDateFormat(DATE_ISO8601_FORMAT).format(date);
      } else {
        return date.toInstant().toString();
      }
    } else {
      return null;
    }
  }
}
