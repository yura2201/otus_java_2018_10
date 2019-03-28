package ru.otus.java.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintRestAmountCommand extends Command {

  private static final Logger LOG = LoggerFactory.getLogger(PrintRestAmountCommand.class);

  public PrintRestAmountCommand(Department department) {
    super(department);
  }

  @Override
  public void execute() {
    LOG.info("departmentRestAmount={}", getDepartment().printRestAmount());
  }
}
