package com.delains.model.suppliers;

import java.math.BigDecimal;

public class SupplierType {

	private BigDecimal id;
	private String type;

	public SupplierType(BigDecimal id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	public SupplierType() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SupplierType [id=" + id + ", type=" + type + "]";
	}

}
