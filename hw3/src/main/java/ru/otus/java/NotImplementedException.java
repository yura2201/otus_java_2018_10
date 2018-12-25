package ru.otus.java;

public class NotImplementedException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "This method is not implemented yet";

  private static final long serialVersionUID = 6622184115583670181L;

  public NotImplementedException() {
    super(DEFAULT_MESSAGE);
  }

  public NotImplementedException(Throwable cause) {
    super(DEFAULT_MESSAGE, cause);
  }
}
