package com.delains.model.purchases;

import java.math.BigDecimal;

import com.delains.model.items.Item;
import com.delains.model.payments.MediumOfPayment;
import com.delains.model.suppliers.Supplier;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Purchase {

	private SimpleObjectProperty<BigDecimal> id = new SimpleObjectProperty<>();
	private SimpleStringProperty date = new SimpleStringProperty();
	private SimpleObjectProperty<Item> itemId = new SimpleObjectProperty<>();
	private SimpleObjectProperty<BigDecimal> quantityPurchased = new SimpleObjectProperty<>();
	private SimpleStringProperty quantityPurchasedStr = new SimpleStringProperty();
	private SimpleObjectProperty<BigDecimal> price = new SimpleObjectProperty<>();
	private SimpleStringProperty priceStr = new SimpleStringProperty();
	private SimpleObjectProperty<BigDecimal> totalCost = new SimpleObjectProperty<>();
	private SimpleStringProperty totalCostStr = new SimpleStringProperty();
	private SimpleBooleanProperty credit = new SimpleBooleanProperty();
	private SimpleObjectProperty<Supplier> supplierId = new SimpleObjectProperty<>();
	private SimpleObjectProperty<BigDecimal> amountPaid = new SimpleObjectProperty<>();
	private SimpleStringProperty amountPaidStr = new SimpleStringProperty();
	private SimpleObjectProperty<BigDecimal> balanceToBeCleared = new SimpleObjectProperty<>();
	private SimpleStringProperty balanceToBeClearedStr = new SimpleStringProperty();
	private SimpleObjectProperty<BigDecimal> discountReceived = new SimpleObjectProperty<>();
	private SimpleStringProperty discountReceivedStr = new SimpleStringProperty();

	private SimpleObjectProperty<MediumOfPayment> mediumOfPaymentId = new SimpleObjectProperty<>();

	public Purchase() {

	}

	public Purchase( BigDecimal id, String date, Item itemId, BigDecimal quantityPurchased, BigDecimal price,
			BigDecimal totalCost, boolean credit, Supplier supplierId, BigDecimal amountPaid,
			BigDecimal balanceToBeCleared, BigDecimal discountReceived ) {
		super();
		this.id = new SimpleObjectProperty<>(id);
		this.date = new SimpleStringProperty(date);
		this.itemId = new SimpleObjectProperty<>(itemId);
		this.quantityPurchased = new SimpleObjectProperty<>(quantityPurchased);
		this.price = new SimpleObjectProperty<>(price);
		this.totalCost = new SimpleObjectProperty<>(totalCost);
		this.credit = new SimpleBooleanProperty(credit);
		this.supplierId = new SimpleObjectProperty<>(supplierId);
		this.amountPaid = new SimpleObjectProperty<>(amountPaid);
		this.balanceToBeCleared = new SimpleObjectProperty<>(balanceToBeCleared);
		this.discountReceived = new SimpleObjectProperty<>(discountReceived);
	}

	public BigDecimal getId() {
		return id.get();
	}

	public SimpleObjectProperty<BigDecimal> idProperty() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id.set(id);
	}

	public String getDate() {
		return date.get();
	}

	public SimpleStringProperty dateProperty() {
		return date;
	}

	public void setDate(String date) {
		this.date.set(date);
	}

	public Item getItemId() {
		return itemId.get();
	}

	public SimpleObjectProperty<Item> itemIdProperty() {
		return itemId;
	}

	public void setItemId(Item itemId) {
		this.itemId.set(itemId);
	}

	public BigDecimal getQuantityPurchased() {
		return quantityPurchased.get();
	}

	public SimpleObjectProperty<BigDecimal> quantityPurchasedProperty() {
		return quantityPurchased;
	}

	public void setQuantityPurchased(BigDecimal quantityPurchased) {
		this.quantityPurchased.set(quantityPurchased);
	}

	public String getQuantityPurchasedStr() {
		return quantityPurchasedStr.get();
	}

	public SimpleStringProperty quantityPurchasedStrProperty() {
		return quantityPurchasedStr;
	}

	public void setQuantityPurchasedStr(String quantityPurchasedStr) {
		this.quantityPurchasedStr.set(quantityPurchasedStr);
	}

	public BigDecimal getPrice() {
		return price.get();
	}

	public SimpleObjectProperty<BigDecimal> priceProperty() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price.set(price);
	}

	public String getPriceStr() {
		return priceStr.get();
	}

	public SimpleStringProperty priceStrProperty() {
		return priceStr;
	}

	public void setPriceStr(String priceStr) {
		this.priceStr.set(priceStr);
	}

	public BigDecimal getTotalCost() {
		return totalCost.get();
	}

	public SimpleObjectProperty<BigDecimal> totalCostProperty() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost.set(totalCost);
	}

	public String getTotalCostStr() {
		return totalCostStr.get();
	}

	public SimpleStringProperty totalCostStrProperty() {
		return totalCostStr;
	}

	public void setTotalCostStr(String totalCostStr) {
		this.totalCostStr.set(totalCostStr);
	}

	public boolean isCredit() {
		return credit.get();
	}

	public SimpleBooleanProperty creditProperty() {
		return credit;
	}

	public void setCredit(boolean credit) {
		this.credit.set(credit);
	}

	public Supplier getSupplierId() {
		return supplierId.get();
	}

	public SimpleObjectProperty<Supplier> supplierIdProperty() {
		return supplierId;
	}

	public void setSupplierId(Supplier supplierId) {
		this.supplierId.set(supplierId);
	}

	public BigDecimal getAmountPaid() {
		return amountPaid.get();
	}

	public SimpleObjectProperty<BigDecimal> amountPaidProperty() {
		return amountPaid;
	}

	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid.set(amountPaid);
	}

	public String getAmountPaidStr() {
		return amountPaidStr.get();
	}

	public SimpleStringProperty amountPaidStrProperty() {
		return amountPaidStr;
	}

	public void setAmountPaidStr(String amountPaidStr) {
		this.amountPaidStr.set(amountPaidStr);
	}

	public BigDecimal getBalanceToBeCleared() {
		return balanceToBeCleared.get();
	}

	public SimpleObjectProperty<BigDecimal> balanceToBeClearedProperty() {
		return balanceToBeCleared;
	}

	public void setBalanceToBeCleared(BigDecimal balanceToBeCleared) {
		this.balanceToBeCleared.set(balanceToBeCleared);
	}

	public String getBalanceToBeClearedStr() {
		return balanceToBeClearedStr.get();
	}

	public SimpleStringProperty balanceToBeClearedStrProperty() {
		return balanceToBeClearedStr;
	}

	public void setBalanceToBeClearedStr(String balanceToBeClearedStr) {
		this.balanceToBeClearedStr.set(balanceToBeClearedStr);
	}

	public BigDecimal getDiscountReceived() {
		return discountReceived.get();
	}

	public SimpleObjectProperty<BigDecimal> discountReceivedProperty() {
		return discountReceived;
	}

	public void setDiscountReceived(BigDecimal discountReceived) {
		this.discountReceived.set(discountReceived);
	}

	public String getDiscountReceivedStr() {
		return discountReceivedStr.get();
	}

	public SimpleStringProperty discountReceivedStrProperty() {
		return discountReceivedStr;
	}

	public void setDiscountReceivedStr(String discountReceivedStr) {
		this.discountReceivedStr.set(discountReceivedStr);
	}

	public MediumOfPayment getMediumOfPaymentId() {
		return mediumOfPaymentId.get();
	}

	public SimpleObjectProperty<MediumOfPayment> mediumOfPaymentIdProperty() {
		return mediumOfPaymentId;
	}

	public void setMediumOfPaymentId(MediumOfPayment mediumOfPaymentId) {
		this.mediumOfPaymentId.set(mediumOfPaymentId);
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", date=" + date + ", itemId=" + itemId + ", quantityPurchased="
				+ quantityPurchased + ", price=" + price + ", totalCost=" + totalCost + ", credit=" + credit
				+ ", supplierId=" + supplierId + ", amountPaid=" + amountPaid + ", balanceToBeCleared="
				+ balanceToBeCleared + ", discountReceived=" + discountReceived + "]";
	}

}
