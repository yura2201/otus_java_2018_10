package ru.otus.java;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Created by yurait6@gmail.com on 28.11.2018.
 */
public class MyArrayList<E> implements List<E> {

  private Object[] array;
  private static int DEFAULT_CAPACITY = 10;
  private static int RESIZE_FRACT = 2;
  private static int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
  private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

  private static String INVALID_SIZE = "Invalid list size";

  private int size = 0;

  public MyArrayList() {
    this.array = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
  }

  public MyArrayList(int initialCapacity) {
    if (initialCapacity < 0 || initialCapacity > MAX_ARRAY_SIZE) {
      throw new RuntimeException(INVALID_SIZE);
    } else {
      this.array = new Object[initialCapacity];
    }
  }

  public MyArrayList(Collection<? extends E> c) {
    array = c.toArray();
    if ((size = array.length) != 0) {
      // c.toArray might (incorrectly) not return Object[] (see 6260652)
      if (array.getClass() != Object[].class)
        array = Arrays.copyOf(array, size, Object[].class);
    } else {
      // replace with empty array.
      this.array = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public boolean contains(Object o) {
    return indexOf(o) >= 0;
  }

  /**
   * Returns an iterator over the elements in this list in proper sequence.
   *
   * @return an iterator over the elements in this list in proper sequence
   */
  @Override
  public Iterator<E> iterator() {
    return new MyIterator();
  }

  @Override
  public Object[] toArray() {
    return Arrays.copyOf(array, size);
  }

  private void ensureCapacity(int minCapacity) {
    int capacity = array.length == 0 ? Math.max(DEFAULT_CAPACITY, minCapacity) : minCapacity;
    rangeCheck(capacity, MAX_ARRAY_SIZE);
    if (capacity > array.length) {
      array = Arrays.copyOf(array, getNewCapacity(capacity));
    }
  }

  private int getNewCapacity(int capacity) {
    int newCapacity = array.length / RESIZE_FRACT + array.length;
    if (newCapacity < 0) {
      newCapacity = capacity;
    }
    if (newCapacity - MAX_ARRAY_SIZE > 0)
      newCapacity = hugeCapacity(capacity);
    return newCapacity;
  }

  private static int hugeCapacity(int minCapacity) {
    if (minCapacity < 0)
      throw new OutOfMemoryError();
    return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
  }

  private void rangeCheck(int index) {
    rangeCheck(index, size);
  }

  private void rangeCheck(int index, int maxIndex) {
    if (index > maxIndex || index < 0) {
      throw new IndexOutOfBoundsException(
          String.format("Invalid index [%d]. It must not be negative and max allowed index is [%d]",
              index, maxIndex));
    }
  }

  @Override
  public <T> T[] toArray(T[] a) {
    throw new NotImplementedException();
  }

  @Override
  public boolean add(E e) {
    add(size, e);
    return true;
  }

  @Override
  public void add(int index, E element) {
    rangeCheck(index, size + 1);
    ensureCapacity(size + 1);
    array[index] = element;
    size++;
  }

  @Override
  public boolean addAll(int index, Collection<? extends E> c) {
    rangeCheck(index);
    Object[] a = c.toArray();
    int numNew = a.length;
    ensureCapacity(size + numNew);

    int numMoved = size - index;
    if (numMoved > 0)
      System.arraycopy(array, index, array, index + numNew, numMoved);

    System.arraycopy(a, 0, array, index, numNew);
    size += numNew;
    return numNew != 0;
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    boolean success = false;
    for (E t : c) {
      success |= add(t);
    }
    return success;
  }

  @Override
  public boolean remove(Object o) {
    throw new NotImplementedException();
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    for (Object e : c)
      if (!contains(e))
        return false;
    return true;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw new NotImplementedException();
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new NotImplementedException();
  }

  @Override
  public void clear() {
    for (int i = 0; i < size; i++)
      array[i] = null;
    size = 0;
  }

  @SuppressWarnings("unchecked")
  E elementData(int index) {
    return (E) array[index];
  }

  @Override
  public E get(int index) {
    rangeCheck(index);
    return elementData(index);
  }

  @Override
  public E set(int index, E element) {
    rangeCheck(index);
    E oldValue = elementData(index);
    array[index] = element;
    return oldValue;
  }

  @Override
  public E remove(int index) {
    throw new NotImplementedException();
  }

  @Override
  public int indexOf(Object o) {
    for (int i = 0; i < size; i++) {
      if (Objects.equals(o, array[i])) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public int lastIndexOf(Object o) {
    throw new NotImplementedException();
  }

  @Override
  public ListIterator<E> listIterator() {
    return new MyListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    throw new NotImplementedException();
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    throw new NotImplementedException();
  }

  private class MyIterator implements Iterator<E> {

    protected int position = 0;

    @Override
    public boolean hasNext() {
      return position < size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E next() {
      if (position > size) {
        throw new NoSuchElementException();
      }
      return (E) array[position++];
    }
  }

  private class MyListIterator extends MyIterator implements ListIterator<E> {

    @Override
    public boolean hasPrevious() {
      throw new NotImplementedException();
    }

    @Override
    public E previous() {
      throw new NotImplementedException();
    }

    @Override
    public int nextIndex() {
      throw new NotImplementedException();
    }

    @Override
    public int previousIndex() {
      throw new NotImplementedException();
    }

    @Override
    public void remove() {
      throw new NotImplementedException();
    }

    @Override
    public void set(E e) {
      MyArrayList.this.set(position - 1, e);
    }

    @Override
    public void add(E e) {
      throw new NotImplementedException();
    }

  }
}
