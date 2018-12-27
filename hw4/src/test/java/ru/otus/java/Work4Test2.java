package ru.otus.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.java.annotation.After;
import ru.otus.java.annotation.Before;
import ru.otus.java.annotation.Test;

public class Work4Test2 {

  private static final Logger LOG = LoggerFactory.getLogger(Work4Test2.class);

  @Before
  public void before1() {
    LOG.debug(">>> Instance=[{}], before 1", this);
  }

  @Before
  public void before2() {
    LOG.debug(">>> Instance=[{}], before 2", this);
  }

  @Test
  public void test1() {
    LOG.debug(">>> Instance=[{}], test2", this);
  }

  @Test
  public void test2() {
    LOG.debug(">>> Instance=[{}], test 2", this);
  }

  @After
  public void after1() {
    LOG.debug(">>> Instance=[{}], after 1", this);
  }

  @After
  public void after2() {
    LOG.debug(">>> Instance=[{}], after 2", this);
  }
}
