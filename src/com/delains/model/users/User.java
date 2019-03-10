package com.delains.model.users;

import java.math.BigDecimal;

public class User {

	private BigDecimal id;
	private String userName;
	private String userEmail;
	private String userPhone;
	private String userPassword;
	private boolean admin;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(BigDecimal id, String userName, String userEmail, String userPhone, String userPassword,
			boolean admin) {
		super();
		this.id = id;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.userPassword = userPassword;
		this.admin = admin;
	}


	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", userEmail=" + userEmail + ", userPhone=" + userPhone
				+ ", userPassword=" + userPassword + ", admin=" + admin + "]";
	}

}
