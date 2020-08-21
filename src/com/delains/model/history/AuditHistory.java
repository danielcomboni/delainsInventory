package com.delains.model.history;

import java.math.BigDecimal;

import com.delains.model.users.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AuditHistory {

	private ObjectProperty<BigDecimal> id;
	private StringProperty date ;
	private StringProperty action ;
	private ObjectProperty<User> userId;

	public AuditHistory(BigDecimal id, String date, String action, User userid) {
		this.id = new SimpleObjectProperty<>(id);
		this.date = new SimpleStringProperty(date);
		this.action = new SimpleStringProperty(action);
		this.userId = new SimpleObjectProperty<>(userid);
	}

	public AuditHistory() {
		this(BigDecimal.ZERO,null,null,null);
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

	public String getDate() {
		return date.get();
	}

	public StringProperty dateProperty() {
		return date;
	}

	public void setDate(String date) {
		this.date.set(date);
	}

	public String getAction() {
		return action.get();
	}

	public StringProperty actionProperty() {
		return action;
	}

	public void setAction(String action) {
		this.action.set(action);
	}

	public User getUserId() {
		return userId.get();
	}

	public ObjectProperty<User> userIdProperty() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId.set(userId);
	}

	@Override
	public String toString() {
		return "AuditHistory [id=" + id + ", date=" + date + ", action=" + action + ", userid=" + userId + "]";
	}



}
