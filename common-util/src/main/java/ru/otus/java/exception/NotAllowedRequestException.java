package ru.otus.java.exception;

public class NotAllowedRequestException extends RuntimeException {

  private static final long serialVersionUID = -1725641875908759864L;

  public NotAllowedRequestException(String message) {
    super(message);
  }
}
