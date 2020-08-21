package com.delains.model.sales;

import java.math.BigDecimal;

import com.delains.model.customers.Customer;
import com.delains.model.items.Item;
import com.delains.model.pos.POS;
import com.delains.model.pricing.Pricing;

public class Sale {

	private BigDecimal id;
	private String date;
	private Item itemId;
	private BigDecimal quantitySold;
	private Pricing pricingId;
	private BigDecimal price;
	private boolean credit;
	private BigDecimal discountAllowed;
	private BigDecimal amountReceived;
	private BigDecimal balancToBeReceievd;
	private Customer customerId;
	private POS posId;

	public Sale(BigDecimal id, String date, Item itemId, BigDecimal quantitySold, BigDecimal price, boolean credit,
			BigDecimal discountAllowed, BigDecimal amountReceived, BigDecimal balancToBeReceievd, Customer customerId) {
		super();
		this.id = id;
		this.date = date;
		this.itemId = itemId;
		this.quantitySold = quantitySold;
		this.price = price;
		this.credit = credit;
		this.discountAllowed = discountAllowed;
		this.amountReceived = amountReceived;
		this.balancToBeReceievd = balancToBeReceievd;
		this.customerId = customerId;
	}

	public POS getPosId() {
		return posId;
	}

	public void setPosId(POS posId) {
		this.posId = posId;
	}

	public Pricing getPricingId() {
		return pricingId;
	}

	public void setPricingId(Pricing pricingId) {
		this.pricingId = pricingId;
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

	public BigDecimal getQuantitySold() {
		return quantitySold;
	}

	public void setQuantitySold(BigDecimal quantitySold) {
		this.quantitySold = quantitySold;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isCredit() {
		return credit;
	}

	public void setCredit(boolean credit) {
		this.credit = credit;
	}

	public BigDecimal getDiscountAllowed() {
		return discountAllowed;
	}

	public void setDiscountAllowed(BigDecimal discountAllowed) {
		this.discountAllowed = discountAllowed;
	}

	public BigDecimal getAmountReceived() {
		return amountReceived;
	}

	public void setAmountReceived(BigDecimal amountReceived) {
		this.amountReceived = amountReceived;
	}

	public BigDecimal getBalancToBeReceievd() {
		return balancToBeReceievd;
	}

	public void setBalancToBeReceievd(BigDecimal balancToBeReceievd) {
		this.balancToBeReceievd = balancToBeReceievd;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "Sale [id=" + id + ", date=" + date + ", itemId=" + itemId + ", quantitySold=" + quantitySold
				+ ", price=" + price + ", credit=" + credit + ", discountAllowed=" + discountAllowed
				+ ", amountReceived=" + amountReceived + ", balancToBeReceievd=" + balancToBeReceievd + ", customerId="
				+ customerId + "]";
	}

}
