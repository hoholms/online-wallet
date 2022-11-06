package com.endava.online_wallet.entity;


public class DepositTransfer {

  private long id;
  private String username;
  private long fromAccount;
  private long toDepositType;
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


  public long getFromAccount() {
    return fromAccount;
  }

  public void setFromAccount(long fromAccount) {
    this.fromAccount = fromAccount;
  }


  public long getToDepositType() {
    return toDepositType;
  }

  public void setToDepositType(long toDepositType) {
    this.toDepositType = toDepositType;
  }


  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }


  public java.sql.Timestamp getTransferDate() {
    return transferDate;
  }

  public void setTransferDate(java.sql.Timestamp transferDate) {
    this.transferDate = transferDate;
  }

}
