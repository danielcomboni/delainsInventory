package com.delains.model.logs;

import java.math.BigDecimal;

import com.delains.model.users.User;

public class Login {

	private BigDecimal id;
	private User userId;
	private String dateTimeIn;
	private String dateTimeOut;

	public Login(BigDecimal id, User userId, String dateTimeIn, String dateTimeOut) {
		super();
		this.id = id;
		this.userId = userId;
		this.dateTimeIn = dateTimeIn;
		this.dateTimeOut = dateTimeOut;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getDateTimeIn() {
		return dateTimeIn;
	}

	public void setDateTimeIn(String dateTimeIn) {
		this.dateTimeIn = dateTimeIn;
	}

	public String getDateTimeOut() {
		return dateTimeOut;
	}

	public void setDateTimeOut(String dateTimeOut) {
		this.dateTimeOut = dateTimeOut;
	}

	@Override
	public String toString() {
		return "Login [id=" + id + ", userId=" + userId + ", dateTimeIn=" + dateTimeIn + ", dateTimeOut=" + dateTimeOut
				+ "]";
	}

}
