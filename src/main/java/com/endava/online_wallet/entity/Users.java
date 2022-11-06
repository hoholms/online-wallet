package com.endava.online_wallet.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class Users {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private String username;
  @Column
  private String password;
  @Column
  private boolean enabled;


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

}
