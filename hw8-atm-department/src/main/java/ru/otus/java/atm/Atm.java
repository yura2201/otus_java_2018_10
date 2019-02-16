package ru.otus.java.atm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import ru.otus.java.exception.NotAllowedRequestException;

public class Atm {

  public static final String NOT_ENOUGH_MONEY = "Not enough money in the ATM. Please change requested amount";
  public static final String INVALID_AMOUNT = "Requested amount cannot be issued. Please change requested amount";

  private Map<Nominal, Cell> cells;
  private final Map<Cell, CellMemento> mementos = new HashMap<>();
  private Map<Nominal, Integer> valuesCount;
  private Long totalAmount;
  private List<Nominal> sortedValues = Arrays.asList(Nominal.values()).stream()
      .sorted((val1, val2) -> val1.getValue() > val2.getValue() ? -1 : 1)
      .collect(Collectors.toCollection(ArrayList::new));

  public Atm() {
    this.valuesCount = new HashMap<>();
    this.cells = new HashMap<>();
    Random ran = new Random();
    for (Nominal value : Nominal.values()) {
      Cell cell = new Cell(ran.nextInt(500) + 1, value);
      mementos.put(cell, cell.saveMenento());
      cells.put(value, cell);
      valuesCount.compute(value, (key, val) -> cell.getSize());
    }
    this.totalAmount = 0l;
  }

  public void reset() {
    cells.values().forEach(item -> {
      CellMemento memento = mementos.get(item);
      item.restoreMemento(memento);
      totalAmount += item.getValue().getValue() * item.getSize();
      valuesCount.compute(item.getValue(), (key, val) -> item.getSize());
    });
  }

  public void put(List<Banknote> banknotes) {
    if (Objects.nonNull(banknotes)) {
      banknotes.forEach(item -> {
        cells.get(item.getValue()).put(item);
        totalAmount += item.getValue().getValue();
        valuesCount.compute(item.getValue(), (key, val) -> val + 1);
      });
    }
  }

  public void put(Banknote banknote) {
    if (Objects.nonNull(banknote)) {
      put(Arrays.asList(banknote));
    }
  }

  public List<Banknote> get(long requestAmount) {
    validateRequest(requestAmount);
    long restAmount = requestAmount;
    List<Banknote> result = new ArrayList<>();
    Cell cell;
    for (Nominal val : sortedValues) {
      int count = (int) restAmount / val.getValue();
      if (count > 0) {
        cell = cells.get(val);
        if (cell.getSize() > 0) {
          int allowedSize = cell.getSize() >= count ? count : cell.getSize();
          result.addAll(cell.get(allowedSize));
          valuesCount.compute(val, (key, value) -> value - allowedSize);
          restAmount = restAmount - allowedSize * val.getValue();
          if (restAmount == 0) {
            break;
          }
        }
      }
    }
    return result;
  }

  public long printRestAmount() {
    return cells.entrySet().stream()
        .map(item -> item.getKey().getValue() * item.getValue().getSize()).reduce(0, Integer::sum);
  }

  private void validateRequest(long requestAmount) {
    if (requestAmount > totalAmount || requestAmount < Nominal.FIFTY.getValue()) {
      throw new NotAllowedRequestException(NOT_ENOUGH_MONEY);
    }
    if (requestAmount % Nominal.FIFTY.getValue() * 1.0 != 0) {
      throw new NotAllowedRequestException(INVALID_AMOUNT);
    }
    long restAmount = requestAmount;
    Map<Nominal, Integer> tmpValuesCount = new HashMap<>(valuesCount);
    for (Nominal val : sortedValues) {
      int count = (int) restAmount / val.getValue();
      if (count > 0) {
        Integer cellValueCount = tmpValuesCount.get(val);
        if (cellValueCount > 0) {
          int allowedSize = cellValueCount >= count ? count : cellValueCount;
          tmpValuesCount.compute(val, (k, v) -> v - allowedSize);
          restAmount = restAmount - allowedSize * val.getValue();
          if (restAmount == 0) {
            break;
          }
        }
      }
    }
    if (restAmount != 0) {
      throw new NotAllowedRequestException(INVALID_AMOUNT);
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Atm [cells=");
    builder.append(cells);
    builder.append(", totalAmount=");
    builder.append(totalAmount);
    builder.append("]");
    return builder.toString();
  }
}
