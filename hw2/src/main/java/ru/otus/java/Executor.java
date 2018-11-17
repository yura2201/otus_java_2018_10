package ru.otus.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

  private int size = 20_000_000;

  public Executor() {
  }

  public Executor(int size) {
    this.size = size;
  }

  /**
   * Calculates an average size of the element of the type &lt;T&gt;
   * @param supplier object instance supplier
   * @throws InterruptedException because of using Threads methods
   */
  @SuppressWarnings("unchecked")
  void execute(Supplier<T> supplier) throws InterruptedException {

    LOG.info("pid: {}, initial size=[{}]",
        ManagementFactory.getRuntimeMXBean().getName(), size);

    long mem = getMem();
    System.out.println("Mem: " + mem);

    List<T> refList = new ArrayList<>(size);

    long mem2 = getMem();
    LOG.info("list size of[{}]. reference size[{}]", size, (mem2 - mem) / size);

    refList.clear();
    ((ArrayList<T>) refList).trimToSize();
    int refListSize = refList.size();

    List<T> objectList = Stream.generate(supplier).limit(size).collect(Collectors.toList());

    long mem3 = getMem();
    LOG.info("Element size: " + (mem3 - mem2) / objectList.size());

    objectList.clear();
    ((ArrayList<T>) objectList).trimToSize();
    objectList = null;
    LOG.info("---------------------------------------------------------");

    Thread.sleep(1000); //wait for 1 sec
  }

  private long getMem() throws InterruptedException {
    System.gc();
    Thread.sleep(10);
    Runtime runtime = Runtime.getRuntime();
    return runtime.totalMemory() - runtime.freeMemory();
  }
}

