package com.delains.model.stock;

import java.math.BigDecimal;

import com.delains.model.items.Item;

public class Stock {

	private BigDecimal id;
	private Item itemId;
	private BigDecimal itemQuantity;
	private String date;

	public Stock(BigDecimal id, Item itemId, BigDecimal itemQuantity, String date) {
		super();
		this.id = id;
		this.itemId = itemId;
		this.itemQuantity = itemQuantity;
		this.date = date;
	}

	public Stock() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public Item getItemId() {
		return itemId;
	}

	public void setItemId(Item itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(BigDecimal itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", itemId=" + itemId + ", itemQuantity=" + itemQuantity + ", date=" + date + "]";
	}

}
