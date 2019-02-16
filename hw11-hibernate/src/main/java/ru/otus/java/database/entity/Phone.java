package ru.otus.java.database.entity;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "phone")
public class Phone {

  @Id
  @Basic(optional = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String number;
  @Column(name = "r_user_id", insertable = false, updatable = false)
  private Long refUserId;
  @JoinColumn(name = "r_user_id")
  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
  private User user;

  public Phone(String number) {
    this.number = number;
  }

  public Phone() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Long getRefUserId() {
    return refUserId;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Phone)) {
      return false;
    }
    return super.equals(obj);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Phone [id=");
    builder.append(id);
    builder.append(", number=");
    builder.append(number);
    builder.append("]");
    return builder.toString();
  }

}
