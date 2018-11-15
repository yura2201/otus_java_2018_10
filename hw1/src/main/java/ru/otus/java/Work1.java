package ru.otus.java;

import static com.google.common.base.Objects.equal;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.BasicConfigurator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by yurait6@gmail.com on 15.11.2018.
 */
public class Work1 {


  private static final Logger LOG = LoggerFactory.getLogger(Work1.class);

  public static void main(String[] args) {
    BasicConfigurator.configure();
    LOG.info("Hello world!");
    System.out.println("Input string");
    Scanner sc = new Scanner(System.in);
    String input = sc.nextLine();
    LOG.info("Are strings equals: {}", equal(input, "asd"));

    List<String> list1 = new ArrayList<>(Arrays.asList("h", "a", "z"));
    List<String> list2 = new ArrayList<>(Arrays.asList("b", "i", "y"));
    List<String> result = CollectionUtils.collate(list1, list2);
    LOG.info("Collated list: {}", result);
  }
}

