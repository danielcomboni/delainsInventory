package com.delains.model.purchases;

import java.math.BigDecimal;

import com.delains.model.items.Item;
import com.delains.model.payments.MediumOfPayment;
import com.delains.model.suppliers.Supplier;

public class Purchase {

	private BigDecimal id;
	private String date;
	private Item itemId;
	private BigDecimal quantityPurchased;
	private String quantityPurchasedStr;
	private BigDecimal price;
	private String priceStr;
	private BigDecimal totalCost;
	private String totalCostStr;
	private boolean credit;
	private Supplier supplierId;
	private BigDecimal amountPaid;
	private String amountPaidStr;
	private BigDecimal balanceToBeCleared;
	private String balanceToBeClearedStr;
	private BigDecimal discountReceived;
	private String discountReceivedStr;

	private MediumOfPayment mediumOfPaymentId;

	public Purchase() {

	}

	public Purchase( BigDecimal id, String date, Item itemId, BigDecimal quantityPurchased, BigDecimal price,
			BigDecimal totalCost, boolean credit, Supplier supplierId, BigDecimal amountPaid,
			BigDecimal balanceToBeCleared, BigDecimal discountReceived ) {
		super();
		this.id = id;
		this.date = date;
		this.itemId = itemId;
		this.quantityPurchased = quantityPurchased;
		this.price = price;
		this.totalCost = totalCost;
		this.credit = credit;
		this.supplierId = supplierId;
		this.amountPaid = amountPaid;
		this.balanceToBeCleared = balanceToBeCleared;
		this.discountReceived = discountReceived;
	}

	public MediumOfPayment getMediumOfPaymentId() {
		return mediumOfPaymentId;
	}

	public void setMediumOfPaymentId( MediumOfPayment mediumOfPaymentId ) {
		this.mediumOfPaymentId = mediumOfPaymentId;
	}

	public String getTotalCostStr() {
		return totalCostStr;
	}

	public void setTotalCostStr( String totalCostStr ) {
		this.totalCostStr = totalCostStr;
	}

	public String getQuantityPurchasedStr() {
		return quantityPurchasedStr;
	}

	public void setQuantityPurchasedStr( String quantityPurchasedStr ) {
		this.quantityPurchasedStr = quantityPurchasedStr;
	}

	public String getPriceStr() {
		return priceStr;
	}

	public void setPriceStr( String priceStr ) {
		this.priceStr = priceStr;
	}

	public String getAmountPaidStr() {
		return amountPaidStr;
	}

	public void setAmountPaidStr( String amountPaidStr ) {
		this.amountPaidStr = amountPaidStr;
	}

	public String getBalanceToBeClearedStr() {
		return balanceToBeClearedStr;
	}

	public void setBalanceToBeClearedStr( String balanceToBeClearedStr ) {
		this.balanceToBeClearedStr = balanceToBeClearedStr;
	}

	public String getDiscountReceivedStr() {
		return discountReceivedStr;
	}

	public void setDiscountReceivedStr( String discountReceivedStr ) {
		this.discountReceivedStr = discountReceivedStr;
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

	public Item getItemId() {
		return itemId;
	}

	public void setItemId( Item itemId ) {
		this.itemId = itemId;
	}

	public BigDecimal getQuantityPurchased() {
		return quantityPurchased;
	}

	public void setQuantityPurchased( BigDecimal quantityPurchased ) {
		this.quantityPurchased = quantityPurchased;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice( BigDecimal price ) {
		this.price = price;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost( BigDecimal totalCost ) {
		this.totalCost = totalCost;
	}

	public boolean isCredit() {
		return credit;
	}

	public void setCredit( boolean credit ) {
		this.credit = credit;
	}

	public Supplier getSupplierId() {
		return supplierId;
	}

	public void setSupplierId( Supplier supplierId ) {
		this.supplierId = supplierId;
	}

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid( BigDecimal amountPaid ) {
		this.amountPaid = amountPaid;
	}

	public BigDecimal getBalanceToBeCleared() {
		return balanceToBeCleared;
	}

	public void setBalanceToBeCleared( BigDecimal balanceToBeCleared ) {
		this.balanceToBeCleared = balanceToBeCleared;
	}

	public BigDecimal getDiscountReceived() {
		return discountReceived;
	}

	public void setDiscountReceived( BigDecimal discountReceived ) {
		this.discountReceived = discountReceived;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", date=" + date + ", itemId=" + itemId + ", quantityPurchased="
				+ quantityPurchased + ", price=" + price + ", totalCost=" + totalCost + ", credit=" + credit
				+ ", supplierId=" + supplierId + ", amountPaid=" + amountPaid + ", balanceToBeCleared="
				+ balanceToBeCleared + ", discountReceived=" + discountReceived + "]";
	}

}
