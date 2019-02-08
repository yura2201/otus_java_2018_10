package ru.otus.java.exception;

public class InconsistentResultException extends RuntimeException {

  private static final long serialVersionUID = 3953861250006500540L;

  private static final String DEFAULT_MESSAGE = "ResultSet has returned more then one row by the given ID";

  public InconsistentResultException() {
    super(DEFAULT_MESSAGE);
  }
}
