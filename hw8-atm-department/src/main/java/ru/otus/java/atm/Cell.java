package ru.otus.java.atm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cell {

  private static final Logger LOG = LoggerFactory.getLogger(Cell.class);

  private final List<Banknote> cartridge = new ArrayList<>();

  private Nominal value;

  public Cell(Nominal value) {
    this.value = value;
  }

  public Cell(int size, Nominal value) {
    this(value);
    cartridge.addAll(Collections.nCopies(size, new Banknote(value)));
  }

  public void put(Banknote banknote) {
    cartridge.add(banknote);
    LOG.info("Cell[{}] - put. Current size=[{}]", value, getSize());
  }

  public void put(List<Banknote> banknotes) {
    cartridge.addAll(banknotes);
    LOG.info("Cell[{}] - put. Current size=[{}]", value, getSize());
  }

  public int getSize() {
    return cartridge.size();
  }

  public List<Banknote> get(int size) {
    LOG.info("Cell[{}] - get. Current size=[{}]. Requested size=[{}]", value, getSize(), size);
    if (size > cartridge.size()) {
      throw new IndexOutOfBoundsException();
    }
    List<Banknote> result = new ArrayList<>();
    Iterator<Banknote> iter = cartridge.iterator();
    int i = 0;
    while (i < size) {
      result.add(iter.next());
      iter.remove();
      i++;
    }
    LOG.info("Cell[{}] - get. Rest size=[{}]", value, getSize());
    return result;
  }

  public Nominal getValue() {
    return value;
  }

  public void reset(int size) {
    cartridge.clear();
    ((ArrayList<Banknote>) cartridge).trimToSize();
    cartridge.addAll(Collections.nCopies(size, new Banknote(value)));
  }

  public CellMemento saveMenento() {
    return new CellMemento(value, getSize());
  }

  public void restoreMemento(CellMemento memento) {
    this.value = memento.getNominal();
    reset(memento.getCount());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Cell)) {
      return false;
    }
    return super.equals(obj);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Cell [size=");
    builder.append(getSize());
    builder.append(", value=");
    builder.append(value);
    builder.append("]");
    return builder.toString();
  }

}
