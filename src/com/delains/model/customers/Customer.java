package com.delains.model.customers;

import java.math.BigDecimal;

public class Customer {

	private BigDecimal id;
	private String customerEmail;
	private String customerPhone;
	private String customerName;
	private String date;

	public Customer(BigDecimal id, String customerEmail, String customerPhone, String customerName, String date) {
		super();
		this.id = id;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.customerName = customerName;
		this.date = date;
	}

	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", customerEmail=" + customerEmail + ", customerPhone=" + customerPhone
				+ ", customerName=" + customerName + ", date=" + date + "]";
	}

}
