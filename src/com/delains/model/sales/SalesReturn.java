package com.delains.model.sales;

import java.math.BigDecimal;

import com.delains.model.customers.Customer;
import com.delains.model.items.Item;
import com.delains.model.pos.POS;

public class SalesReturn {

	private BigDecimal id;
	private String date;
	private Item itemId;
	private BigDecimal quantityReturned;
	private BigDecimal quantityReStocked;
	private BigDecimal quantityDiscarded;
	private Customer customerId;
	private POS posId;
	private String reason;

	public SalesReturn() {
		// TODO Auto-generated constructor stub
	}

	public String getReason() {
		return reason;
	}

	public void setReason( String reason ) {
		this.reason = reason;
	}

	public String getDate() {
		return date;
	}

	public void setDate( String date ) {
		this.date = date;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId( BigDecimal id ) {
		this.id = id;
	}

	public Item getItemId() {
		return itemId;
	}

	public void setItemId( Item itemId ) {
		this.itemId = itemId;
	}

	public BigDecimal getQuantityReturned() {
		return quantityReturned;
	}

	public void setQuantityReturned( BigDecimal quantityReturned ) {
		this.quantityReturned = quantityReturned;
	}

	public BigDecimal getQuantityReStocked() {
		return quantityReStocked;
	}

	public void setQuantityReStocked( BigDecimal quantityReStocked ) {
		this.quantityReStocked = quantityReStocked;
	}

	public BigDecimal getQuantityDiscarded() {
		return quantityDiscarded;
	}

	public void setQuantityDiscarded( BigDecimal quantityDiscarded ) {
		this.quantityDiscarded = quantityDiscarded;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId( Customer customerId ) {
		this.customerId = customerId;
	}

	public POS getPosId() {
		return posId;
	}

	public void setPosId( POS posId ) {
		this.posId = posId;
	}

	@Override
	public String toString() {
		return "SalesReturn [id=" + id + ", itemId=" + itemId + ", quantityReturned=" + quantityReturned
				+ ", quantityReStocked=" + quantityReStocked + ", quantityDiscarded=" + quantityDiscarded
				+ ", customerId=" + customerId + ", posId=" + posId + "]";
	}

}
