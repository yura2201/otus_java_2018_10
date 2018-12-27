package ru.otus.java.core;

public class ExecutableContainer {

  private String[] beforeMethodNames;
  private String[] testMethodNames;
  private String[] afterMethodNames;

  public String[] getBeforeMethodNames() {
    return beforeMethodNames;
  }

  public void setBeforeMethodNames(String[] beforeMethodNames) {
    this.beforeMethodNames = beforeMethodNames;
  }

  public String[] getTestMethodNames() {
    return testMethodNames;
  }

  public void setTestMethodNames(String[] testMethodNames) {
    this.testMethodNames = testMethodNames;
  }

  public String[] getAfterMethodNames() {
    return afterMethodNames;
  }

  public void setAfterMethodNames(String[] afterMethodNames) {
    this.afterMethodNames = afterMethodNames;
  }

}
