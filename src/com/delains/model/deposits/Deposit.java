package com.delains.model.deposits;

import java.math.BigDecimal;

import com.delains.model.customers.Customer;
import com.delains.model.payments.MediumOfPayment;
import com.delains.model.suppliers.Supplier;

public class Deposit {

	private BigDecimal id;
	private String date;
	private BigDecimal amountDeposited;
	private MediumOfPayment mediumOfPaymentFromId;
	private MediumOfPayment mediumOfPaymentToId;
	private Customer customerId;
	private String reason;

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

	public BigDecimal getAmountDeposited() {
		return amountDeposited;
	}

	public void setAmountDeposited( BigDecimal amountDeposited ) {
		this.amountDeposited = amountDeposited;
	}

	public MediumOfPayment getMediumOfPaymentFromId() {
		return mediumOfPaymentFromId;
	}

	public void setMediumOfPaymentFromId( MediumOfPayment mediumOfPaymentFromId ) {
		this.mediumOfPaymentFromId = mediumOfPaymentFromId;
	}

	public MediumOfPayment getMediumOfPaymentToId() {
		return mediumOfPaymentToId;
	}

	public void setMediumOfPaymentToId( MediumOfPayment mediumOfPaymentToId ) {
		this.mediumOfPaymentToId = mediumOfPaymentToId;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId( Customer customerId ) {
		this.customerId = customerId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason( String reason ) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "Deposit [id=" + id + ", date=" + date + ", amountDeposited=" + amountDeposited
				+ ", mediumOfPaymentFromId=" + mediumOfPaymentFromId + ", mediumOfPaymentToId=" + mediumOfPaymentToId
				+ ", customerId=" + customerId + ", reason=" + reason + "]";
	}

}
