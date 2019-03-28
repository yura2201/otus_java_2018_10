package ru.otus.java.atm;

public class ResetCommand extends Command {

  public ResetCommand(Department department) {
    super(department);
  }

  @Override
  public void execute() {
    getDepartment().reset();
  }
}
