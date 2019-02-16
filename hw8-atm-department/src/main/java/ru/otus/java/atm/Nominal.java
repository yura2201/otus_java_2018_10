package ru.otus.java.atm;

public enum Nominal {

  FIFTY(50), HUNDRED(100), TWO_HUNDRED(200), FIVE_HUNDRED(500), THOUSAND(1000), TWO_THOUSAND(
      2000), FIVE_THOUSAND(5000);

  private final int value;

  Nominal(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
