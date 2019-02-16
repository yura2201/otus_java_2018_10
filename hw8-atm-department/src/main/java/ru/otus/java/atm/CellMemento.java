package ru.otus.java.atm;

public class CellMemento {

  private final Nominal nominal;
  private final int count;

  public CellMemento(Nominal nominal, int count) {
    super();
    this.nominal = nominal;
    this.count = count;
  }

  public Nominal getNominal() {
    return nominal;
  }

  public int getCount() {
    return count;
  }
}
