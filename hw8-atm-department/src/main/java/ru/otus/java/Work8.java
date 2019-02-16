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
    dep.setAtms(Arrays.asList(atm, new Atm(), new Atm()));
    LOG.info("department={}", dep);
    atm.get(value);
    LOG.info("department={}", dep);
  }
}
