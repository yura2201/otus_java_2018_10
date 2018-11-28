package ru.otus.java;

import org.apache.log4j.BasicConfigurator;

/**
 * Created by yurait6@gmail.com on 15.11.2018.
 */
public class Work3 {

    public static void main(String[] args) throws InterruptedException {
        BasicConfigurator.configure();
        System.out.println("Hello world!");
        Work3 work3 = new Work3();
        work3.start();
    }

    private void start() throws InterruptedException {
        System.out.println(2 >> 1);
        System.out.println(3 >> 1);
        System.out.println(4 >> 1);
    }
}

