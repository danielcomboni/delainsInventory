package com.delains.model.purchases;

import java.math.BigDecimal;

import com.delains.model.items.Item;
import com.delains.model.suppliers.Supplier;

public class PurchaseReturn {

	private BigDecimal id;
	private String date;
	private Item itemId;
	private BigDecimal quantity;
	private String reason;
	private boolean moneyBack;
	private Supplier supplierId;
	private Purchase purchaseId;

	public PurchaseReturn() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Item getItemId() {
		return itemId;
	}

	public void setItemId(Item itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isMoneyBack() {
		return moneyBack;
	}

	public void setMoneyBack(boolean moneyBack) {
		this.moneyBack = moneyBack;
	}

	public Supplier getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Supplier supplierId) {
		this.supplierId = supplierId;
	}

	public Purchase getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Purchase purchaseId) {
		this.purchaseId = purchaseId;
	}

	@Override
	public String toString() {
		return "PurchaseReturn [id=" + id + ", date=" + date + ", itemId=" + itemId + ", quantity=" + quantity
				+ ", reason=" + reason + ", moneyBack=" + moneyBack + ", supplierId=" + supplierId + ", purchaseId="
				+ purchaseId + "]";
	}

}
