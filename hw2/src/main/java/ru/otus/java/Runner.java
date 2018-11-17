package ru.otus.java;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created by yurait6@gmail.com on 15.11.2018.
 *
 * ДЗ 02. Измерение памяти
 * Написать стенд для определения размера объекта.
 * Определить размер пустой строки и пустых контейнеров.
 * Определить рост размера контейнера от количества элементов в нем.
 *
 *
 * Например:
 * Object — 8 bytes,
 * Empty String — 40 bytes
 * Array — from 12 bytes
 */
class Runner {

  private int size = 20_000_000;

  public Runner() {
  }

  void run() throws InterruptedException {
    run(10);
    run(1000);
    run(100_000);
    run(1_000_000);
    run(null);
  }

  void run(Integer size) throws InterruptedException {
    if (Objects.isNull(size)) {
      runRefString();
      runObject();
      runByteString();
      runInteger();
      runTestClass();
    } else {
      runRefString(size);
      runObject(size);
      runByteString(size);
      runInteger(size);
      runTestClass(size);
    }
  }

  void runObject() throws InterruptedException {
    Executor<Object> executor = new Executor<>();
    executor.execute(Object::new);
  }

  void runObject(int size) throws InterruptedException {
    Executor<Object> executor = new Executor<>(size);
    executor.execute(Object::new);
  }

  void runInteger() throws InterruptedException {
    Executor<Integer> executor = new Executor<>();
    executor.execute(() -> new Integer(1));
  }

  void runByteString() throws InterruptedException {
    Executor<String> executor = new Executor<>();
    executor.execute(() -> new String(new byte[0]));
  }

  void runRefString() throws InterruptedException {
    Executor<String> executor = new Executor<>();
    executor.execute(String::new);
  }

  void runTestClass(int size) throws InterruptedException {
    Executor<TestClass> executor = new Executor<>(size);
    executor.execute(TestClass::new);
  }

  void runInteger(int size) throws InterruptedException {
    Executor<Integer> executor = new Executor<>(size);
    executor.execute(() -> new Integer(1));
  }

  void runByteString(int size) throws InterruptedException {
    Executor<String> executor = new Executor<>(size);
    executor.execute(() -> new String(new byte[0]));
  }

  void runRefString(int size) throws InterruptedException {
    Executor<String> executor = new Executor<>(size);
    executor.execute(String::new);
  }

  void runTestClass() throws InterruptedException {
    Executor<TestClass> executor = new Executor<>();
    executor.execute(TestClass::new);
  }
}

