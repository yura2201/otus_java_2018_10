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

  private final Values value;

  public Cell(Values value) {
    this.value = value;
  }

  public Cell(int size, Values value) {
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

  public Values getValue() {
    return value;
  }

  public void reset(int size) {
    cartridge.clear();
    ((ArrayList<Banknote>) cartridge).trimToSize();
    cartridge.addAll(Collections.nCopies(size, new Banknote(value)));
  }
}
