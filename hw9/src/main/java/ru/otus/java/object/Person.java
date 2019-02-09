package ru.otus.java.object;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Person {

  private String name;
  private int age;
  private Date birthDate;
  private BigDecimal income;
  private List<Phone> phones;
  private String[] documents;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public BigDecimal getIncome() {
    return income;
  }

  public void setIncome(BigDecimal income) {
    this.income = income;
  }

  public List<Phone> getPhones() {
    return phones;
  }

  public void setPhones(List<Phone> phones) {
    this.phones = phones;
  }

  public String[] getDocuments() {
    return documents;
  }

  public void setDocuments(String[] documents) {
    this.documents = documents;
  }
}
