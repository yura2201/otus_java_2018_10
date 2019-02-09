package ru.otus.java;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import ru.otus.java.core.JsonObjectWritter;
import ru.otus.java.object.Person;
import ru.otus.java.object.Phone;

public class Work9Test {

  @Test
  public void testJsonWritter() throws Exception {
    Phone phone1 = new Phone();
    phone1.setType(1);
    phone1.setNumber("322-223-322");
    Phone phone2 = new Phone();
    phone2.setType(2);
    phone2.setNumber("223-322-223");

    Person person = new Person();
    person.setAge(123);
    person.setBirthDate(new Date());
    person.setIncome(new BigDecimal("12345.5678"));
    person.setName("John Doe");
    person.setDocuments(new String[] { "passport", "driver license" });
    person.setPhones(Arrays.asList(phone1, phone2));

    String jsonString = JsonObjectWritter.toJson(person);

    Gson gson = new Gson();
    Person deserializedPerson = gson.fromJson(jsonString, Person.class);

    Assert.assertNotNull("Expected non null person object", deserializedPerson);
    Assert.assertEquals("Expected person.name=[" + person.getName() + "] but actual is=["
        + deserializedPerson.getName() + "]", person.getName(), deserializedPerson.getName());
    Assert.assertEquals("Expected person.age=[" + person.getAge() + "] but actual is=["
        + deserializedPerson.getAge() + "]", person.getAge(), deserializedPerson.getAge());
    Assert.assertArrayEquals(
        "Expected person.phones=[" + person.getPhones() + "] but actual is=["
            + deserializedPerson.getPhones() + "]",
        person.getPhones().toArray(), deserializedPerson.getPhones().toArray());
    Assert.assertArrayEquals(
        "Expected person.documents=[" + person.getDocuments() + "] but actual is=["
            + deserializedPerson.getDocuments() + "]",
        person.getDocuments(), deserializedPerson.getDocuments());
  }
}
