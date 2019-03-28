package ru.otus.java.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResetListener implements EventListener {

  private static final Logger LOG = LoggerFactory.getLogger(ResetListener.class);

  @Override
  public void update() {
    LOG.info("Reset perfomed");
  }

}
