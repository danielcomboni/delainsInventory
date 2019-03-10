package com.delains.model.expenses;

import java.math.BigDecimal;

import com.delains.model.payments.MediumOfPayment;

public class Expenses {

	private BigDecimal id;
	private String date;
	private BigDecimal amountSpent;
	private String reason;
	private MediumOfPayment mediumOfPaymentId;

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

	public BigDecimal getAmountSpent() {
		return amountSpent;
	}

	public void setAmountSpent( BigDecimal amountSpent ) {
		this.amountSpent = amountSpent;
	}

	public String getReason() {
		return reason;
	}

	public void setReason( String reason ) {
		this.reason = reason;
	}

	public MediumOfPayment getMediumOfPaymentId() {
		return mediumOfPaymentId;
	}

	public void setMediumOfPaymentId( MediumOfPayment mediumOfPaymentId ) {
		this.mediumOfPaymentId = mediumOfPaymentId;
	}

	@Override
	public String toString() {
		return "Expenses [id=" + id + ", date=" + date + ", amountSpent=" + amountSpent + ", reason=" + reason
				+ ", mediumOfPaymentId=" + mediumOfPaymentId + "]";
	}

}
