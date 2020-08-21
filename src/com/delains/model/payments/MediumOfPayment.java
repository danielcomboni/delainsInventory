package com.delains.model.payments;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class MediumOfPayment {

	private ObjectProperty<BigDecimal> id;
	private StringProperty nameOfMediumOfPayment;
	private StringProperty specificationOrDescription;

	public MediumOfPayment(){
		this(BigDecimal.ZERO,null,null);
	}

	public MediumOfPayment(BigDecimal id, String nameOfMediumOfPayment, String specificationOrDescription) {
		this.id = new SimpleObjectProperty<>(id);
		this.nameOfMediumOfPayment = new SimpleStringProperty(nameOfMediumOfPayment);
		this.specificationOrDescription = new SimpleStringProperty(specificationOrDescription);
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

	public String getNameOfMediumOfPayment() {
		return nameOfMediumOfPayment.get();
	}

	public StringProperty nameOfMediumOfPaymentProperty() {
		return nameOfMediumOfPayment;
	}

	public void setNameOfMediumOfPayment(String nameOfMediumOfPayment) {
		this.nameOfMediumOfPayment.set(nameOfMediumOfPayment);
	}

	public String getSpecificationOrDescription() {
		return specificationOrDescription.get();
	}

	public StringProperty specificationOrDescriptionProperty() {
		return specificationOrDescription;
	}

	public void setSpecificationOrDescription(String specificationOrDescription) {
		this.specificationOrDescription.set(specificationOrDescription);
	}

	@Override
	public String toString() {
		return "MediumOfPayment{" +
				"id=" + id +
				", nameOfMediumOfPayment=" + nameOfMediumOfPayment +
				", specificationOrDescription=" + specificationOrDescription +
				'}';
	}
}
