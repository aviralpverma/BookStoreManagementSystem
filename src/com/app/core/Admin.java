package com.app.core;

public class Admin extends User {
	
	public enum AdminType {
		SUPER,NORMAL;
	}

	private AdminType adminType;
	
	public Admin(String email, String password, AdminType adminType) {
		super(email, password);
		this.adminType = adminType;
	}
	
	public AdminType getAdminType() {
		return adminType;
	}

	@Override
	public String toString() {
		return "Admin [" + super.toString() + ", adminType=" + adminType + "]";
	}
}
