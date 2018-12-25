package ru.otus.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yurait6@gmail.com on 15.11.2018.
 */
public class Work3 {

  private static final Logger LOG = LoggerFactory.getLogger(Work3.class);

  public static void main(String[] args) throws InterruptedException {
    BasicConfigurator.configure();
    System.out.println("Hello world!");
    Work3 work3 = new Work3();
    work3.start();
  }

  private void start() throws InterruptedException {
    List<Integer> sourceList = Arrays.asList(1, 2, 3, 4, 5);
    List<Integer> sourceList2 = Arrays.asList(6, 7, 8);
    List<Integer> myList = new MyArrayList<>(sourceList);
    List<Integer> stdList = new ArrayList<>(sourceList);

    Integer[] addAllArray = new Integer[10000];
    Date start = new Date();
    Arrays.fill(addAllArray, 10);
    boolean result = Collections.addAll(myList, addAllArray);
    Date stop = new Date();
    LOG.debug("MyArrayList - addAll[{}] - 10000 items - time=[{}]ms", result,
        stop.getTime() - start.getTime());

    start = new Date();
    result = Collections.addAll(stdList, addAllArray);
    stop = new Date();
    LOG.debug("ArrayList - addAll[{}] - 10000 items - time=[{}]ms", result,
        stop.getTime() - start.getTime());
  }
}
