package ru.otus.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class Work3Test {

  private static final List<Integer> SOURCE_LIST = Arrays.asList(1, 2, 3, 4, 5);

  private static final List<Integer> SOURCE_LIST_2 = Arrays.asList(6, 7, 8);

  @Test
  public void testAddAll() throws Exception {
    Integer[] addAllArray = new Integer[10000];
    Arrays.fill(addAllArray, 10);
    List<Integer> myList = new MyArrayList<>(SOURCE_LIST);
    boolean result = Collections.addAll(myList, addAllArray);

    Assert.assertTrue("Expected [true] but actual [false]", result);
    Assert.assertTrue("Expected size[10005] but actual[" + myList.size() + "]",
        myList.size() == 10005);
  }

  @Test
  public void testCopyMyToMy() throws Exception {
    List<Integer> myList = new MyArrayList<>(SOURCE_LIST);
    List<Integer> myList2 = new MyArrayList<>(SOURCE_LIST_2);

    Collections.copy(myList, myList2);

    Assert.assertTrue("Expected size[5] but actual[" + myList.size() + "]", myList.size() == 5);
    List<Integer> ethalon = new ArrayList<>(SOURCE_LIST);
    Collections.copy(ethalon, SOURCE_LIST_2);
    Assert.assertArrayEquals(ethalon.toArray(), myList.toArray());
  }

  @Test
  public void testCopyMyToStd() throws Exception {
    List<Integer> list = new ArrayList<>(SOURCE_LIST);
    List<Integer> myList2 = new MyArrayList<>(SOURCE_LIST_2);

    Collections.copy(list, myList2);

    Assert.assertTrue("Expected size[5] but actual[" + list.size() + "]", list.size() == 5);
    List<Integer> ethalon = new ArrayList<>(SOURCE_LIST);
    Collections.copy(ethalon, SOURCE_LIST_2);
    Assert.assertArrayEquals(ethalon.toArray(), list.toArray());
  }

  @Test
  public void testCopyStdToMy() throws Exception {
    List<Integer> myList = new MyArrayList<>(SOURCE_LIST);
    List<Integer> list2 = new ArrayList<>(SOURCE_LIST_2);

    Collections.copy(myList, list2);

    Assert.assertTrue("Expected size[5] but actual[" + myList.size() + "]", myList.size() == 5);
    List<Integer> ethalon = new ArrayList<>(SOURCE_LIST);
    Collections.copy(ethalon, SOURCE_LIST_2);
    Assert.assertArrayEquals(ethalon.toArray(), myList.toArray());
  }

  @Test
  public void testSort() throws Exception {
    List<Integer> myList = new MyArrayList<>(SOURCE_LIST_2);
    myList.addAll(SOURCE_LIST);
    Collections.sort(myList, Comparator.comparingInt(Integer::intValue));

    for (int i = 0; i < SOURCE_LIST.size(); i++) {
      Assert.assertEquals(SOURCE_LIST.get(i), myList.get(i));
    }
    int j = 0;
    for (int i = SOURCE_LIST.size(); i < SOURCE_LIST_2.size(); i++) {
      Assert.assertEquals(SOURCE_LIST_2.get(j), myList.get(i));
    }
  }

}
