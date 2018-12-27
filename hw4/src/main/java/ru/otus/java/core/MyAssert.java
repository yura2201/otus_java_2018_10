package ru.otus.java.core;

public class MyAssert {

  private MyAssert() {
  }

  static public void assertTrue(String message, boolean condition) {
    if (!condition) {
      fail(message);
    }
  }

  static public void assertTrue(boolean condition) {
    assertTrue(null, condition);
  }

  static public void assertFalse(String message, boolean condition) {
    assertTrue(message, !condition);
  }

  static public void assertFalse(boolean condition) {
    assertFalse(null, condition);
  }

  static public void fail(String message) {
    if (message == null) {
      throw new AssertionError();
    }
    throw new AssertionError(message);
  }

  static public void fail() {
    fail(null);
  }
}
