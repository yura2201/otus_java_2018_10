package ru.otus.java;

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

  void run() throws InterruptedException {
    Executor<String> executor = new Executor<>();
    executor.execute(() -> new String(new byte[0]));
  }
}

