package ru.otus.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.java.annotation.After;
import ru.otus.java.annotation.Before;
import ru.otus.java.annotation.Test;
import ru.otus.java.core.MyAssert;

public class Work4Test1 {

  private static final Logger LOG = LoggerFactory.getLogger(Work4Test1.class);

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
    LOG.debug(">>> Instance=[{}], test1", this);
    MyAssert.assertTrue(1 == 1);
  }

  @Test
  public void test2() {
    LOG.debug(">>> Instance=[{}], test 2", this);
    MyAssert.assertFalse(1 == 2);
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
