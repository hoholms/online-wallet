package com.endava.online_wallet.entity;


public class IncomeType {

  private long id;
  private String username;
  private String type;
  private String isDefault;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(String isDefault) {
    this.isDefault = isDefault;
  }

}
