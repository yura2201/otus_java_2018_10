package ru.otus.java.atm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Department {

  private List<Atm> atms;
  private final EventManager events;

  public Department() {
    this.events = new EventManager("reset", "printRest");
  }

  public long printRestAmount() {
    events.notify("printRest");
    return atms.stream().map(Atm::printRestAmount).reduce(0L, Long::sum);
  }

  public void reset() {
    atms.forEach(Atm::reset);
    events.notify("reset");
  }

  public List<Atm> getAtms() {
    return atms;
  }

  public void setAtms(List<Atm> atms) {
    this.atms = atms;
  }

  public void addAtms(List<Atm> atms) {
    if (Objects.isNull(getAtms())) {
      this.atms = new ArrayList<>();
    }
    this.atms.addAll(atms);
  }

  public void addAtm(Atm atm) {
    if (Objects.isNull(getAtms())) {
      this.atms = new ArrayList<>();
    }
    this.atms.add(atm);
  }

  public EventManager getEvents() {
    return events;
  }
}
