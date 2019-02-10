package ru.otus.java.atm;

public class Banknote {

  private final Values value;

  public Banknote(Values value) {
    this.value = value;
  }

  public Values getValue() {
    return value;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Banknote [value=");
    builder.append(value);
    builder.append("]");
    return builder.toString();
  }
}
