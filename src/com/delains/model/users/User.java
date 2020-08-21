package com.delains.model.users;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;

public class User {

	private final SimpleObjectProperty<BigDecimal> id;
	private final SimpleStringProperty userName ;
	private final SimpleStringProperty userEmail ;
	private final SimpleStringProperty userPhone ;
	private final SimpleStringProperty userPassword ;
	private final SimpleBooleanProperty admin ;

	public User() {
		this(BigDecimal.ZERO,null,null,null,null,false);
	}

	public User(BigDecimal id, String userName, String userEmail, String userPhone, String userPassword, boolean admin) {
		this.id = new SimpleObjectProperty<>(id);
		this.userName = new SimpleStringProperty(userName);
		this.userEmail = new SimpleStringProperty(userEmail);
		this.userPhone = new SimpleStringProperty(userPhone);
		this.userPassword = new SimpleStringProperty(userPassword);
		this.admin = new SimpleBooleanProperty(admin);
	}

	/*
	public User(BigDecimal id, String userName, String userEmail, String userPhone, String userPassword,
			boolean admin) {
		super();
		this.id = id;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.userPassword = userPassword;
		this.admin = admin;
	}*/


	public BigDecimal getId() {
		return id.get();
	}

	public SimpleObjectProperty<BigDecimal> idProperty() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id.set(id);
	}

	public String getUserName() {
		return userName.get();
	}

	public SimpleStringProperty userNameProperty() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName.set(userName);
	}

	public String getUserEmail() {
		return userEmail.get();
	}

	public SimpleStringProperty userEmailProperty() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail.set(userEmail);
	}

	public String getUserPhone() {
		return userPhone.get();
	}

	public SimpleStringProperty userPhoneProperty() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone.set(userPhone);
	}

	public String getUserPassword() {
		return userPassword.get();
	}

	public SimpleStringProperty userPasswordProperty() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword.set(userPassword);
	}

	public boolean isAdmin() {
		return admin.get();
	}

	public SimpleBooleanProperty adminProperty() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin.set(admin);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", userEmail=" + userEmail + ", userPhone=" + userPhone
				+ ", userPassword=" + userPassword + ", admin=" + admin + "]";
	}

}
