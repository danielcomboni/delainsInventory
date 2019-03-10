package com.delains.model.suppliers;

import java.math.BigDecimal;

public class Supplier {

	private BigDecimal id;
	private String supplierEmail;
	private String supplierPhone;
	private String supplierName;
	private String date;
	private SupplierType type;
	private String typeName;

	public Supplier(BigDecimal id, String supplierEmail, String supplierPhone, String supplierName, String date,
			SupplierType type) {
		super();
		this.id = id;
		this.supplierEmail = supplierEmail;
		this.supplierPhone = supplierPhone;
		this.supplierName = supplierName;
		this.date = date;
		this.type = type;
	}

	public Supplier() {
		// TODO Auto-generated constructor stub
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getSupplierEmail() {
		return supplierEmail;
	}

	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}

	public String getSupplierPhone() {
		return supplierPhone;
	}

	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public SupplierType getType() {
		return type;
	}

	public void setType(SupplierType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Supplier [id=" + id + ", supplierEmail=" + supplierEmail + ", supplierPhone=" + supplierPhone
				+ ", supplierName=" + supplierName + ", date=" + date + ", type=" + type + "]";
	}

}
