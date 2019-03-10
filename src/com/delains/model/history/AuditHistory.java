package com.delains.model.history;

import java.math.BigDecimal;

import com.delains.model.users.User;

public class AuditHistory {

	private BigDecimal id;
	private String date;
	private String action;
	private User userid;

	public AuditHistory() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId( BigDecimal id ) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate( String date ) {
		this.date = date;
	}

	public String getAction() {
		return action;
	}

	public void setAction( String action ) {
		this.action = action;
	}

	public User getUserid() {
		return userid;
	}

	public void setUserid( User userid ) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "AuditHistory [id=" + id + ", date=" + date + ", action=" + action + ", userid=" + userid + "]";
	}

}
