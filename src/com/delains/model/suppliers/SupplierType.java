package com.delains.model.suppliers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class SupplierType {

	private ObjectProperty<BigDecimal> id;
	private StringProperty type;

	public SupplierType(BigDecimal id, String type) {
		this.id = new SimpleObjectProperty<>(id);
		this.type = new SimpleStringProperty(type);
	}

	public SupplierType() {
		this(BigDecimal.ZERO,null);
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

	public String getType() {
		return type.get();
	}

	public StringProperty typeProperty() {
		return type;
	}

	public void setType(String type) {
		this.type.set(type);
	}

	@Override
	public String toString() {
		return "SupplierType [id=" + id + ", type=" + type + "]";
	}

}
