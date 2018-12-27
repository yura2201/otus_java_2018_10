package ru.otus.java;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.log4j.BasicConfigurator;

import ru.otus.java.core.Runner;

public class TestRunner {

  public static void main(String[] args)
      throws ClassNotFoundException, IOException, URISyntaxException {
    BasicConfigurator.configure();
    Runner runner = new Runner();
    runner.run("ru.otus.java");
  }
}
