package com.company;

public class Client {

	private String name;
	private String surname;
	private String code;

	public Client(String name, String surname, String code) {
		super();
		this.name = name;
		this.surname = surname;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getCode() {
		return code;
	}
}
