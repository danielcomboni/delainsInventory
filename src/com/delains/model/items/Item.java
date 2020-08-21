package com.delains.model.items;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class Item {

	private  ObjectProperty<BigDecimal> id;
	private  StringProperty itemName;
	private  StringProperty itemDescription;
	private  StringProperty unitOfMeasurement;
	private  StringProperty barcode;
	private  StringProperty packageName;
	private  ObjectProperty<BigDecimal> packageVolume;

	public Item(BigDecimal id, String itemName, String itemDescription, String unitOfMeasurement, String barcode) {

		this.id = new SimpleObjectProperty<>(id);
		this.itemName = new SimpleStringProperty(itemName);
		this.itemDescription = new SimpleStringProperty(itemDescription);
		this.unitOfMeasurement = new SimpleStringProperty(unitOfMeasurement);
		this.barcode = new SimpleStringProperty(barcode);

	}

	public Item(BigDecimal id, String itemName, String itemDescription, String unitOfMeasurement, String barcode, String packageName, BigDecimal packageVolume) {
		this.id = new SimpleObjectProperty<>(id);
		this.itemName = new SimpleStringProperty(itemName);
		this.itemDescription = new SimpleStringProperty(itemDescription);
		this.unitOfMeasurement = new SimpleStringProperty(unitOfMeasurement);
		this.barcode = new SimpleStringProperty(barcode);
		this.packageName = new SimpleStringProperty(packageName);
		this.packageVolume = new SimpleObjectProperty(packageVolume);
	}

	public Item() {
		this(BigDecimal.ZERO,null,null,null,null,null,BigDecimal.ZERO);
	}

	public BigDecimal getId() {
		return id.get();
	}

	public ObjectProperty<BigDecimal> idProperty() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id.set(id);
	}

	public String getItemName() {
		return itemName.get();
	}

	public StringProperty itemNameProperty() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName.set(itemName);
	}

	public String getItemDescription() {
		return itemDescription.get();
	}

	public StringProperty itemDescriptionProperty() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription.set(itemDescription);
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement.get();
	}

	public StringProperty unitOfMeasurementProperty() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement.set(unitOfMeasurement);
	}

	public String getBarcode() {
		return barcode.get();
	}

	public StringProperty barcodeProperty() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode.set(barcode);
	}

	public String getPackageName() {
		return packageName.get();
	}

	public StringProperty packageNameProperty() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName.set(packageName);
	}

	public BigDecimal getPackageVolume() {
		return packageVolume.get();
	}

	public ObjectProperty<BigDecimal> packageVolumeProperty() {
		return packageVolume;
	}

	public void setPackageVolume(BigDecimal packageVolume) {
		this.packageVolume.set(packageVolume);
	}

	@Override
	public String toString() {
		return "Item{" +
				"id=" + id +
				", itemName=" + itemName +
				", itemDescription=" + itemDescription +
				", unitOfMeasurement=" + unitOfMeasurement +
				", barcode=" + barcode +
				", packageName=" + packageName +
				", packageVolume=" + packageVolume +
				'}';
	}
}
