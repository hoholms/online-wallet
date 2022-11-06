package com.endava.online_wallet.entity;


import java.sql.Timestamp;

public class IncomeTransfer {

  private long id;
  private String username;
  private long fromIncomeType;
  private long toAccount;
  private String amount;
  private java.sql.Timestamp transferDate;


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


  public long getFromIncomeType() {
    return fromIncomeType;
  }

  public void setFromIncomeType(long fromIncomeType) {
    this.fromIncomeType = fromIncomeType;
  }


  public long getToAccount() {
    return toAccount;
  }

  public void setToAccount(long toAccount) {
    this.toAccount = toAccount;
  }


  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }


  public Timestamp getTransferDate() {
    return transferDate;
  }

  public void setTransferDate(Timestamp transferDate) {
    this.transferDate = transferDate;
  }

}
