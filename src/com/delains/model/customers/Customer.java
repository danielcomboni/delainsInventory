package com.delains.model.customers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class Customer {

	private ObjectProperty<BigDecimal> id;
	private StringProperty customerEmail;
	private StringProperty customerPhone;
	private StringProperty customerName;
	private StringProperty date;



	public Customer(BigDecimal id, String customerEmail, String customerPhone, String customerName, String date) {
		this.id = new SimpleObjectProperty<>(id);
		this.customerEmail = new SimpleStringProperty(customerEmail);
		this.customerPhone = new SimpleStringProperty(customerPhone);
		this.customerName = new SimpleStringProperty(customerName);
		this.date = new SimpleStringProperty(date);
	}

	public Customer() {
		this(BigDecimal.ZERO, null,null,null,null);
	}

	public BigDecimal getId() {
		return id.get();
	}

	public ObjectProperty<BigDecimal> idProperty() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id.set(id);
	}

	public String getCustomerEmail() {
		return customerEmail.get();
	}

	public StringProperty customerEmailProperty() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail.set(customerEmail);
	}

	public String getCustomerPhone() {
		return customerPhone.get();
	}

	public StringProperty customerPhoneProperty() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone.set(customerPhone);
	}

	public String getCustomerName() {
		return customerName.get();
	}

	public StringProperty customerNameProperty() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName.set(customerName);
	}

	public String getDate() {
		return date.get();
	}

	public StringProperty dateProperty() {
		return date;
	}

	public void setDate(String date) {
		this.date.set(date);
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", customerEmail=" + customerEmail +
				", customerPhone=" + customerPhone +
				", customerName=" + customerName +
				", date=" + date +
				'}';
	}
}
