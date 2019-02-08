package ru.otus.java.exception;

public class AnnotationNotFoundException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "Annotation [%s] not found";

  private static final long serialVersionUID = 6622184115583670181L;

  public AnnotationNotFoundException(String name) {
    super(String.format(DEFAULT_MESSAGE, name));
  }
}
