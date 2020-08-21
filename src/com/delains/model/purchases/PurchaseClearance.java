package com.delains.model.purchases;

import java.math.BigDecimal;

public class PurchaseClearance {

	private BigDecimal id;
	private String date;
	private Purchase purchaseId;
	private BigDecimal amountCleared;
	private BigDecimal balanceToBeCleared;

	public PurchaseClearance() {
		// TODO Auto-generated constructor stub
	}

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

	public Purchase getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId( Purchase purchaseId ) {
		this.purchaseId = purchaseId;
	}

	public BigDecimal getAmountCleared() {
		return amountCleared;
	}

	public void setAmountCleared( BigDecimal amountCleared ) {
		this.amountCleared = amountCleared;
	}

	public BigDecimal getBalanceToBeCleared() {
		return balanceToBeCleared;
	}

	public void setBalanceToBeCleared( BigDecimal balanceToBeCleared ) {
		this.balanceToBeCleared = balanceToBeCleared;
	}

	@Override
	public String toString() {
		return "PurchaseClearance [id=" + id + ", date=" + date + ", purchaseId=" + purchaseId + ", amountCleared="
				+ amountCleared + ", balanceToBeCleared=" + balanceToBeCleared + "]";
	}

}
