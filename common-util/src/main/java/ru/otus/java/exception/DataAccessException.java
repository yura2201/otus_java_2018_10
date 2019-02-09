package ru.otus.java.exception;

public class DataAccessException extends RuntimeException {

  private static final long serialVersionUID = 6877730602022736800L;

  public DataAccessException(String message) {
    super(message);
  }
}
