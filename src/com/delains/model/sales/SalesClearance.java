package com.delains.model.sales;

import java.math.BigDecimal;

import com.delains.model.pos.POS;

public class SalesClearance {

	private BigDecimal id;
	private String date;
	private POS posId;
	private BigDecimal amountCleared;
	private BigDecimal balanceToBeCleared;

	public SalesClearance() {
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

	public POS getPosId() {
		return posId;
	}

	public void setPosId( POS posId ) {
		this.posId = posId;
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
		return "SalesClearance [id=" + id + ", date=" + date + ", posId=" + posId + ", amountCleared=" + amountCleared
				+ ", balanceToBeCleared=" + balanceToBeCleared + "]";
	}

}
