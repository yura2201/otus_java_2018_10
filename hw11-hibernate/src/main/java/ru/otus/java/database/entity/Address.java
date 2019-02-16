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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address {

  @Id
  @Basic(optional = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String street;
  @Column(name = "r_user_id", insertable = false, updatable = false)
  private Long refUserId;
  @JoinColumn(name = "r_user_id")
  @OneToOne(
      fetch = FetchType.LAZY,
      cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
  private User user;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
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
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Address [id=");
    builder.append(id);
    builder.append(", street=");
    builder.append(street);
    builder.append("]");
    return builder.toString();
  }
}
