package ru.otus.java;

/**
 * Created by yurait6@gmail.com on 15.11.2018.
 */
public class Work1 {

    //Не получается обфусцировать с dependency log4j. Куча ошибок вида
    /**
     *  [proguard] Unexpected error while evaluating instruction:
     *  [proguard]   Class       = [org/apache/log4j/LogManager]
     *  [proguard]   Method      = [<clinit>()V]
     *
     *  Пробовал добавлять -keep с ключами по всем методам класса и по тем, которые указаны в трейсе,
     *  но не помогло(
     */

//    private static final Logger LOG = Logger.getLogger(Work1.class);

    public static void main(String[] args)  {
        System.out.println("Hello world!");

    }
}

