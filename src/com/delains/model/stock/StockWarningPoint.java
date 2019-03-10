package com.delains.model.stock;

import java.math.BigDecimal;

import com.delains.model.items.Item;

public class StockWarningPoint {

	private BigDecimal id;
	private Item itemId;
	private BigDecimal quantityLimit;

	public StockWarningPoint() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getQuantityLimit() {
		return quantityLimit;
	}

	public void setQuantityLimit( BigDecimal quantityLimit ) {
		this.quantityLimit = quantityLimit;
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

	@Override
	public String toString() {
		return "StockWarningpoint [id=" + id + ", itemId=" + itemId + ", quantityLimit=" + quantityLimit + "]";
	}

}
