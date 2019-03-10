package com.delains.model.items;

import java.math.BigDecimal;

public class Item {

	private BigDecimal id;
	private String itemName;
	private String itemDescription;
	private String unitOfMeasurement;
	private String barcode;
	private String packageName;
	private BigDecimal packageVolume;

	public Item(BigDecimal id, String itemName, String itemDescription, String unitOfMeasurement, String barcode) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.unitOfMeasurement = unitOfMeasurement;
		this.barcode = barcode;
	}

	public Item() {
		// TODO Auto-generated constructor stub
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public BigDecimal getPackageVolume() {
		return packageVolume;
	}

	public void setPackageVolume(BigDecimal packageVolume) {
		this.packageVolume = packageVolume;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", itemName=" + itemName + ", itemDescription=" + itemDescription
				+ ", unitOfMeasurement=" + unitOfMeasurement + ", barcode=" + barcode + ", packageName=" + packageName
				+ ", packageVolume=" + packageVolume + "]";
	}

}
