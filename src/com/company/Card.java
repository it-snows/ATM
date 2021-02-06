package com.company;

public class Card {
	private int pin;
	private String number; // primitive data type
	private Client client; // complex data type

	public Card(int pin, String number, Client client) {
		// super();
		this.pin = pin;
		this.number = number;
		this.client = client;
	}

	public String getNumber() {
		return number;
	}

	public Client getClient() {
		return client;
	}

	public boolean isPinValid(int pin) {
		// cannot expose pin to external class
		return pin == this.pin; // this keyword because names "pin" match
		// not using if else because boolean which returns true/false
	}
}