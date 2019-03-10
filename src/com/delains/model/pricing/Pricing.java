package com.delains.model.pricing;

import java.math.BigDecimal;

import com.delains.model.items.Item;

public class Pricing {

	private BigDecimal id;
	private Item itemId;
	private BigDecimal price;
	private String priceStr;
	private String date;
	private String itemName;

	public Pricing(BigDecimal id, Item itemId, BigDecimal price, String date) {
		super();
		this.id = id;
		this.itemId = itemId;
		this.price = price;
		this.date = date;
	}

	public Pricing() {
		// TODO Auto-generated constructor stub
	}

	public String getPriceStr() {
		return priceStr;
	}

	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Pricing [id=" + id + ", itemId=" + itemId + ", price=" + price + ", date=" + date + "]";
	}

}
