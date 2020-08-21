package com.delains.model.pos;

import java.math.BigDecimal;

import com.delains.model.customers.Customer;
import com.delains.model.items.Item;
import com.delains.model.payments.MediumOfPayment;
import com.delains.model.pricing.Pricing;

public class POS {

	private BigDecimal id;
	private String date;
	private Item itemId;
	private String barCode;
	private String itemName;
	private Pricing pricing;
	private BigDecimal price;
	private BigDecimal quantity;
	private BigDecimal cost;
	private BigDecimal amountPaid;
	private boolean credit;
	private BigDecimal discountAllowed;
	private Customer customerId;
	private BigDecimal totalCost;
	private BigDecimal change;
	private BigDecimal balanceToBePaidByCustomer;
	private MediumOfPayment mediumOfPaymentId;

	public POS() {
		// TODO Auto-generated constructor stub
	}

	public POS( BigDecimal id, String date, Item itemId, String barCode, String itemName, Pricing pricing,
			BigDecimal price, BigDecimal quantity, BigDecimal cost, BigDecimal amountPaid, boolean credit,
			BigDecimal discountAllowed, Customer customerId, BigDecimal totalCost, BigDecimal change,
			BigDecimal balanceToBePaidByCustomer ) {
		super();
		this.id = id;
		this.date = date;
		this.itemId = itemId;
		this.barCode = barCode;
		this.itemName = itemName;
		this.pricing = pricing;
		this.price = price;
		this.quantity = quantity;
		this.cost = cost;
		this.amountPaid = amountPaid;
		this.credit = credit;
		this.discountAllowed = discountAllowed;
		this.customerId = customerId;
		this.totalCost = totalCost;
		this.change = change;
		this.balanceToBePaidByCustomer = balanceToBePaidByCustomer;
	}

	public MediumOfPayment getMediumOfPaymentId() {
		return mediumOfPaymentId;
	}

	public void setMediumOfPaymentId( MediumOfPayment mediumOfPaymentId ) {
		this.mediumOfPaymentId = mediumOfPaymentId;
	}

	public BigDecimal getChange() {
		return change;
	}

	public void setChange( BigDecimal change ) {
		this.change = change;
	}

	public BigDecimal getBalanceToBePaidByCustomer() {
		return balanceToBePaidByCustomer;
	}

	public void setBalanceToBePaidByCustomer( BigDecimal balanceToBePaidByCustomer ) {
		this.balanceToBePaidByCustomer = balanceToBePaidByCustomer;
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

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid( BigDecimal amountPaid ) {
		this.amountPaid = amountPaid;
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

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode( String barCode ) {
		this.barCode = barCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName( String itemName ) {
		this.itemName = itemName;
	}

	public Pricing getPricing() {
		return pricing;
	}

	public void setPricing( Pricing price ) {
		this.pricing = price;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity( BigDecimal quantity ) {
		this.quantity = quantity;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost( BigDecimal cost ) {
		this.cost = cost;
	}

	public boolean isCredit() {
		return credit;
	}

	public void setCredit( boolean credit ) {
		this.credit = credit;
	}

	public BigDecimal getDiscountAllowed() {
		return discountAllowed;
	}

	public void setDiscountAllowed( BigDecimal discountAllowed ) {
		this.discountAllowed = discountAllowed;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId( Customer customerId ) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "POS [id=" + id + ", date=" + date + ", itemId=" + itemId + ", barCode=" + barCode + ", itemName="
				+ itemName + ", pricing=" + pricing + ", price=" + price + ", quantity=" + quantity + ", cost=" + cost
				+ ", amountPaid=" + amountPaid + ", credit=" + credit + ", discountAllowed=" + discountAllowed
				+ ", customerId=" + customerId + ", totalCost=" + totalCost + ", change=" + change
				+ ", balanceToBePaidByCustomer=" + balanceToBePaidByCustomer + "]";
	}

}
