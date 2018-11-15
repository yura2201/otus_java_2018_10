package ru.otus.java;

import org.apache.log4j.BasicConfigurator;

/**
 * Created by yurait6@gmail.com on 15.11.2018.
 */
public class Work2 {

    public static void main(String[] args) throws InterruptedException {
        BasicConfigurator.configure();
        System.out.println("Hello world!");
        Work2 work2 = new Work2();
        work2.start();
    }

    private void start() throws InterruptedException {
        Runner runner = new Runner();
        runner.run();
    }
}

