package com.delains.model.users;

import java.math.BigDecimal;

public class UserResponsiblity {

	private BigDecimal id;
	private User userId;
	private String responsibilty;

	public UserResponsiblity(BigDecimal id, User userId, String responsibilty) {
		super();
		this.id = id;
		this.userId = userId;
		this.responsibilty = responsibilty;
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

	public String getResponsibilty() {
		return responsibilty;
	}

	public void setResponsibilty(String responsibilty) {
		this.responsibilty = responsibilty;
	}

	@Override
	public String toString() {
		return "UserResponsiblity [id=" + id + ", userId=" + userId + ", responsibilty=" + responsibilty + "]";
	}

}
