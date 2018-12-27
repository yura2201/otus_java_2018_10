package ru.otus.java.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import ru.otus.java.annotation.After;
import ru.otus.java.annotation.Before;
import ru.otus.java.annotation.Test;
import ru.otus.java.util.ReflectionHelper;

public class Runner {

  private static final Object[] EMPTY_ARG = {};

  /**
   * Runs a test for a given class type
   * 
   * @param clazz
   *          Class&lt;?&gt; class type to be executed
   */
  public void run(Class<?> clazz) {
    if (Objects.nonNull(clazz)) {
      ExecutableContainer container = getCommonExecutableContainer(clazz);
      if (Objects.nonNull(container)) {
        execute(clazz, container);
      }
    }
  }

  /**
   * Runs a test or a bunch of tests depends on a <b>name</b> parameter, considering it is a full
   * qualified name of the class or a package name where test classes are placed. First checks for a
   * package case, if no classes were found tries run a single test considering the name is a full
   * qualified class name
   * 
   * @param name
   *          name of the class or a package
   * @throws ClassNotFoundException
   *           if no such a class has been found
   * @throws IOException
   *           because of a package scan
   * @throws URISyntaxException
   *           because of a package scan
   */
  public void run(String name) throws ClassNotFoundException, IOException, URISyntaxException {
    List<String> classes = getClassNamesFromPackage(name);
    if (Objects.isNull(classes) || classes.isEmpty()) {
      Class<?> clazz = Class.forName(name);
      run(clazz);
    } else {
      String fullQualifiedName = name.concat(".");
      for (String className : classes) {
        Class<?> clazz = Class.forName(fullQualifiedName.concat(className));
        run(clazz);
      }
    }
  }

  private void execute(Class<?> clazz, ExecutableContainer container) {
    if (!(Objects.isNull(clazz) || Objects.isNull(container)
        || Objects.isNull(container.getTestMethodNames())
        || container.getTestMethodNames().length == 0)) {
      for (String testMethodName : container.getTestMethodNames()) {
        Object test = ReflectionHelper.instantiate(clazz, EMPTY_ARG);
        executeArray(test, container.getBeforeMethodNames());
        ReflectionHelper.callMethod(test, testMethodName, EMPTY_ARG);
        executeArray(test, container.getAfterMethodNames());
      }
    }
  }

  private void executeArray(Object object, String[] methods) {
    if (Objects.nonNull(methods)) {
      for (String name : methods) {
        ReflectionHelper.callMethod(object, name, EMPTY_ARG);
      }
    }
  }

  private ExecutableContainer getCommonExecutableContainer(Class<?> clazz) {
    @SuppressWarnings("unchecked")
    Set<Method> annotatedMethods = ReflectionHelper.getAnnotatedMethods(clazz, Before.class,
        Test.class, After.class);
    if (!annotatedMethods.isEmpty()) {
      ExecutableContainer result = new ExecutableContainer();
      String[] befores = annotatedMethods.stream()
          .filter(item -> item.isAnnotationPresent(Before.class)).map(Method::getName)
          .toArray(String[]::new);
      String[] tests = annotatedMethods.stream()
          .filter(item -> item.isAnnotationPresent(Test.class)).map(Method::getName)
          .toArray(String[]::new);
      String[] afters = annotatedMethods.stream()
          .filter(item -> item.isAnnotationPresent(After.class)).map(Method::getName)
          .toArray(String[]::new);
      result.setAfterMethodNames(afters);
      result.setBeforeMethodNames(befores);
      result.setTestMethodNames(tests);
      return result;
    } else {
      return null;
    }
  }

  private static List<String> getClassNamesFromPackage(String packageName)
      throws IOException, URISyntaxException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    URL packageURL;
    List<String> names = new ArrayList<String>();

    packageName = packageName.replace(".", "/");
    packageURL = classLoader.getResource(packageName);

    if (packageURL.getProtocol().equals("jar")) {
      String jarFileName;

      Enumeration<JarEntry> jarEntries;
      String entryName;
      // build jar file name, then loop through zipped entries
      jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
      jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
      System.out.println(">" + jarFileName);
      try (JarFile jf = new JarFile(jarFileName)) {
        jarEntries = jf.entries();
        while (jarEntries.hasMoreElements()) {
          entryName = jarEntries.nextElement().getName();
          if (entryName.startsWith(packageName) && entryName.length() > packageName.length() + 5) {
            entryName = entryName.substring(packageName.length(), entryName.lastIndexOf('.'));
            names.add(entryName);
          }
        }
      }

      // loop through files in classpath
    } else {
      URI uri = new URI(packageURL.toString());
      File folder = new File(uri.getPath());
      // won't work with path which contains blank (%20)
      // File folder = new File(packageURL.getFile());
      File[] contenuti = folder.listFiles();
      String entryName;
      for (File actual : contenuti) {
        entryName = actual.getName();
        entryName = entryName.substring(0, entryName.lastIndexOf('.'));
        names.add(entryName);
      }
    }
    return names;
  }
}
