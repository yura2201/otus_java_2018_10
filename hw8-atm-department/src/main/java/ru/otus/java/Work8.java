package ru.otus.java;

import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.java.atm.Atm;
import ru.otus.java.atm.Banknote;
import ru.otus.java.atm.Department;
import ru.otus.java.atm.Nominal;
import ru.otus.java.atm.PrintRestAmountCommand;
import ru.otus.java.atm.PrintRestListener;
import ru.otus.java.atm.ResetCommand;
import ru.otus.java.atm.ResetListener;

/**
 * Created by yurait6@gmail.com on 16.02.2019.
 */
public class Work8 {

  private static final Logger LOG = LoggerFactory.getLogger(Work8.class);

  public static void main(String[] args) {
    BasicConfigurator.configure();
    LOG.info("Hello world!");

    int value = Stream.of(Nominal.values()).map(Nominal::getValue).reduce(0, Integer::sum);
    LOG.info("value={}", value);

    Atm atm = new Atm();
    LOG.info("rest=[{}]", atm.printRestAmount());
    atm.put(Arrays.asList(new Banknote(Nominal.FIVE_THOUSAND), new Banknote(Nominal.FIVE_THOUSAND),
        new Banknote(Nominal.THOUSAND), new Banknote(Nominal.TWO_HUNDRED),
        new Banknote(Nominal.FIFTY), new Banknote(Nominal.FIFTY), new Banknote(Nominal.FIFTY),
        new Banknote(Nominal.FIFTY)));
    LOG.info("rest=[{}]", atm.printRestAmount());

    Department dep = new Department();
    dep.getEvents().subscribe("reset", new ResetListener());
    dep.getEvents().subscribe("printRest", new PrintRestListener());

    PrintRestAmountCommand printCommand = new PrintRestAmountCommand(dep);
    ResetCommand resetCommand = new ResetCommand(dep);

    dep.setAtms(Arrays.asList(atm, new Atm(), new Atm()));
    printCommand.execute();
    atm.get(value);
    printCommand.execute();
    resetCommand.execute();
  }
}
