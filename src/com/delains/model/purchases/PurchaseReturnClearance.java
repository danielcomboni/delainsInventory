package com.delains.model.purchases;

import java.math.BigDecimal;

public class PurchaseReturnClearance {

	private BigDecimal id;
	private String date;
	private BigDecimal amountPaid;
	private PurchaseReturn purchaseReturnId;

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

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid( BigDecimal amountPaid ) {
		this.amountPaid = amountPaid;
	}

	public PurchaseReturn getPurchaseReturnId() {
		return purchaseReturnId;
	}

	public void setPurchaseReturnId( PurchaseReturn purchaseReturnId ) {
		this.purchaseReturnId = purchaseReturnId;
	}

	@Override
	public String toString() {
		return "PurchaseReturnClearance [id=" + id + ", date=" + date + ", amountPaid=" + amountPaid
				+ ", purchaseReturnId=" + purchaseReturnId + "]";
	}

}
