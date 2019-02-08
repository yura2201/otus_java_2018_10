package ru.otus.java.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import ru.otus.java.exception.AnnotationNotFoundException;

/**
 * Created by tully.
 */
public class ReflectionHelper {
  private ReflectionHelper() {
  }

  public static <T> T instantiate(Class<T> type, Object... args) {
    try {
      if (Objects.isNull(args) || args.length == 0) {
        return type.getDeclaredConstructor().newInstance();
      } else {
        Class<?>[] classes = toClasses(args);
        return type.getDeclaredConstructor(classes).newInstance(args);
      }
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException
        | InvocationTargetException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static Object getFieldValue(Object object, String name) {
    Field field = null;
    boolean isAccessible = true;
    try {
      field = object.getClass().getDeclaredField(name); // getField() for public fields
      isAccessible = field.isAccessible();
      field.setAccessible(true);
      return field.get(object);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    } finally {
      if (field != null && !isAccessible) {
        field.setAccessible(false);
      }
    }
    return null;
  }

  public static void setFieldValue(Object object, String name, Object value) {
    Field field = null;
    boolean isAccessible = true;
    try {
      field = object.getClass().getDeclaredField(name); // getField() for public fields
      isAccessible = field.isAccessible();
      field.setAccessible(true);
      field.set(object, value);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    } finally {
      if (field != null && !isAccessible) {
        field.setAccessible(false);
      }
    }
  }

  public static Object callMethod(Object object, String name, Object... args) {
    Method method = null;
    boolean isAccessible = true;
    try {
      method = object.getClass().getDeclaredMethod(name, toClasses(args));
      isAccessible = method.isAccessible();
      method.setAccessible(true);
      return method.invoke(object, args);
    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      e.printStackTrace();
    } finally {
      if (method != null && !isAccessible) {
        method.setAccessible(false);
      }
    }
    return null;
  }

  public static Class<?>[] toClasses(Object[] args) {
    return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
  }

  public static <T> Set<Method> getAnnotatedMethods(Class<T> type,
      Class<? extends Annotation>... annotations) {
    Set<Method> result = new HashSet<>();
    for (Method method : type.getMethods()) {
      for (Class<? extends Annotation> annotation : annotations) {
        if (method.isAnnotationPresent(annotation)) {
          result.add(method);
          break;
        }
      }
    }
    return result;
  }

  public static <T> Field getAnnotatedField(Class<T> type, Class<? extends Annotation> annotation) {
    return Stream.of(type.getDeclaredFields()).filter(item -> item.isAnnotationPresent(annotation))
        .findFirst().orElseThrow(() -> new AnnotationNotFoundException(annotation.getName()));
  }
}
