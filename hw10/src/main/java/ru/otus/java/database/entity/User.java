package ru.otus.java.database.entity;

import ru.otus.java.annotation.Id;

public class User {

  @Id
  private Long id;
  private String name;
  private Integer age;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("User [id=");
    builder.append(id);
    builder.append(", name=");
    builder.append(name);
    builder.append(", age=");
    builder.append(age);
    builder.append("]");
    return builder.toString();
  }

}
