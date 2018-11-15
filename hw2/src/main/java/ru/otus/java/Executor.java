package ru.otus.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by yurait6@gmail.com on 16.11.2018.
 */
class Executor<T> {

  private static Logger LOG = LoggerFactory.getLogger(Executor.class);

  private static final int SIZE = 20_000_000;

  /**
   * Calculates an average size of the element of the type &lt;T&gt;
   * @param supplier object instance supplier
   * @throws InterruptedException because of using Threads methods
   */
  void execute(Supplier<T> supplier) throws InterruptedException {
    LOG.info("pid: {}", ManagementFactory.getRuntimeMXBean().getName());

    long mem = getMem();
    System.out.println("Mem: " + mem);

    List<T> refList = new ArrayList<>(SIZE);

    long mem2 = getMem();
    LOG.info("empty list {} ref size=[{}]", refList.size(), (mem2 - mem) / SIZE);

    refList.clear();
    ((ArrayList<T>) refList).trimToSize();
    refList = null;

    List<T> objectList = Stream.generate(supplier).limit(SIZE).collect(Collectors.toList());

    long mem3 = getMem();
    LOG.info("Element size: " + (mem3 - mem2) / objectList.size());

    objectList.clear();
    ((ArrayList<T>) objectList).trimToSize();
    objectList = null;
    System.out.println("Array is ready for GC");

    Thread.sleep(1000); //wait for 1 sec
  }

  private long getMem() throws InterruptedException {
    System.gc();
    Thread.sleep(10);
    Runtime runtime = Runtime.getRuntime();
    return runtime.totalMemory() - runtime.freeMemory();
  }
}

