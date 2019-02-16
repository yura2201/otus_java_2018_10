package ru.otus.java.database.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

  @Id
  @Basic(optional = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private Integer age;

  @OneToOne(
      mappedBy = "user", fetch = FetchType.LAZY,
      cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
  private Address address;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
  private Set<Phone> phones;

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

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
    address.setUser(this);
  }

  /**
   * Adds a single phone to a user
   * 
   * @param phone
   *          phone to be added
   */
  public void addPhone(Phone phone) {
    if (Objects.isNull(phones)) {
      phones = new HashSet<>();
    }
    phones.add(phone);
    phone.setUser(this);
  }

  public Set<Phone> getPhones() {
    return phones;
  }

  public void setPhones(Set<Phone> phones) {
    if (Objects.nonNull(phones)) {
      if (Objects.nonNull(getPhones())) {
        getPhones().clear();
      }
      phones.forEach(this::addPhone);
    }
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
    builder.append(", address=");
    builder.append(address);
    builder.append(", phones=");
    builder.append(phones);
    builder.append("]");
    return builder.toString();
  }
}
