package com.delains.model.suppliers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class Supplier {

	private ObjectProperty<BigDecimal> id;

	private StringProperty supplierEmail;
	private StringProperty supplierPhone;
	private StringProperty supplierName;

	private StringProperty date;
	private ObjectProperty<SupplierType> type;
	private StringProperty typeName;

	public Supplier(BigDecimal id, String supplierEmail, String supplierPhone, String supplierName, String date,
			SupplierType type) {

		this.id = new SimpleObjectProperty<>(id);
		this.supplierEmail = new SimpleStringProperty(supplierEmail);
		this.supplierPhone = new SimpleStringProperty(supplierPhone);
		this.supplierName = new SimpleStringProperty(supplierName);
		this.date = new SimpleStringProperty(date);
		this.type = new SimpleObjectProperty<>(type);
	}

	public Supplier() {
		this(BigDecimal.ZERO,null,null,null, null,null);
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

	public String getSupplierEmail() {
		return supplierEmail.get();
	}

	public StringProperty supplierEmailProperty() {
		return supplierEmail;
	}

	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail.set(supplierEmail);
	}

	public String getSupplierPhone() {
		return supplierPhone.get();
	}

	public StringProperty supplierPhoneProperty() {
		return supplierPhone;
	}

	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone.set(supplierPhone);
	}

	public String getSupplierName() {
		return supplierName.get();
	}

	public StringProperty supplierNameProperty() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName.set(supplierName);
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

	public SupplierType getType() {
		return type.get();
	}

	public ObjectProperty<SupplierType> typeProperty() {
		return type;
	}

	public void setType(SupplierType type) {
		this.type.set(type);
	}

	public String getTypeName() {
		return typeName.get();
	}

	public StringProperty typeNameProperty() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName.set(typeName);
	}

	@Override
	public String toString() {
		return "Supplier [id=" + id + ", supplierEmail=" + supplierEmail + ", supplierPhone=" + supplierPhone
				+ ", supplierName=" + supplierName + ", date=" + date + ", type=" + type + "]";
	}

}
