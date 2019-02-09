package ru.otus.java.object;

public class Phone {

  private int type;
  private String number;

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((number == null) ? 0 : number.hashCode());
    result = prime * result + type;
    return result;
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
    Phone other = (Phone) obj;
    if (number == null) {
      if (other.number != null) {
        return false;
      }
    } else if (!number.equals(other.number)) {
      return false;
    }
    if (type != other.type) {
      return false;
    }
    return true;
  }
}
