package com.example.transfermoney.model;


public class Account {
	private String id;
	private String accountName;
	private Money balance;
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getAccountName(){
		return accountName;
	}
	
	public void setAccountName(String accountName){
		this.accountName = accountName;
	}
	
	public Money getBalance(){
		return balance;
	}
	
	public void setBalance(Money balance){
		this.balance = balance;
	}
}
