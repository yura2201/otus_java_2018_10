package ru.otus.java.atm;

public abstract class Command {

  private final Department department;

  public Command(Department department) {
    this.department = department;
  }

  public abstract void execute();

  public Department getDepartment() {
    return department;
  }
}
