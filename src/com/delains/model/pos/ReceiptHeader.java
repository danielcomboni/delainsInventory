package com.delains.model.pos;

import java.math.BigDecimal;

public class ReceiptHeader {

	private BigDecimal id;
	private String businessName;
	private String location;
	private String contact;

	public BigDecimal getId() {
		return id;
	}

	public void setId( BigDecimal id ) {
		this.id = id;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName( String businessName ) {
		this.businessName = businessName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation( String location ) {
		this.location = location;
	}

	public String getContact() {
		return contact;
	}

	public void setContact( String contact ) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "ReceiptHeader [id=" + id + ", businessName=" + businessName + ", location=" + location + ", contact="
				+ contact + "]";
	}

}
