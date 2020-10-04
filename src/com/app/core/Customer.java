package com.app.core;

import java.time.LocalDate;

public class Customer extends User {
	private String name;
	private long mobNumber;
	private LocalDate dateOfBirth;
	
	public Customer(String email, String password, String name, long mobNumber, LocalDate dateOfBirth) {
		super(email, password);
		this.name = name;
		this.mobNumber = mobNumber;
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Customer [" + super.toString() + ", name=" + name + ", mobNumber=" + mobNumber + ", dateOfBirth=" + dateOfBirth + "]";
	}
}
