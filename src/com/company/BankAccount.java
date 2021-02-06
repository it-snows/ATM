package com.company;

public class BankAccount {

	private String number;
	private double balance;
	private Client owner;

	public BankAccount(String number, double balance, Client owner) {
//		super();
		this.number = number;
		this.balance = balance;
		this.owner = owner;
	}

	public String getNumber() {
		return number;
	}

	public double getBalance() {
		// balance should be exposed to owner only
		// no external process can change balance
		return balance;
	}

	public Client getOwner() {
		return owner;
	}

	public boolean takeMoney(double amount) {
		if (balance >= amount) {
			balance -= amount; // externally cannot set balance, no direct changes allowed
			return true;
		}
		return false;
	}

}
