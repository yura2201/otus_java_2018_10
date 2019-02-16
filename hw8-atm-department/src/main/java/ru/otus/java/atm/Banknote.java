package ru.otus.java.atm;

public class Banknote {

  private final Nominal value;

  public Banknote(Nominal value) {
    this.value = value;
  }

  public Nominal getValue() {
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
