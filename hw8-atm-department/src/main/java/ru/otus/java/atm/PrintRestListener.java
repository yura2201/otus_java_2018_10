package ru.otus.java.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintRestListener implements EventListener {

  private static final Logger LOG = LoggerFactory.getLogger(PrintRestListener.class);

  @Override
  public void update() {
    LOG.info("Print amount perfomed");
  }
}
