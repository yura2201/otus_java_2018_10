package ru.otus.java;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.java.atm.Atm;
import ru.otus.java.atm.Banknote;
import ru.otus.java.atm.Values;

/**
 * Created by yurait6@gmail.com on 10.02.2019.
 */
public class Work7 {

  private static final Logger LOG = LoggerFactory.getLogger(Work7.class);

  public static void main(String[] args) {
    BasicConfigurator.configure();
    LOG.info("Hello world!");
    Atm atm = new Atm();
    LOG.info("rest=[{}]", atm.printRestAmount());
    atm.put(Arrays.asList(new Banknote(Values.FIVE_THOUSAND), new Banknote(Values.FIVE_THOUSAND),
        new Banknote(Values.THOUSAND), new Banknote(Values.TWO_HUNDRED), new Banknote(Values.FIFTY),
        new Banknote(Values.FIFTY), new Banknote(Values.FIFTY), new Banknote(Values.FIFTY)));
    LOG.info("rest=[{}]", atm.printRestAmount());
    List<Banknote> result = atm.get(1200);
    LOG.info("result=[{}], rest=[{}]", result, atm.printRestAmount());
  }
}
