package com.delains.model.payments;

import java.math.BigDecimal;

public class MediumOfPayment {

	private BigDecimal id;
	private String nameOfMediumOfPayment;
	private String specificationOrDescription;

	public BigDecimal getId() {
		return id;
	}

	public void setId( BigDecimal id ) {
		this.id = id;
	}

	public String getNameOfMediumOfPayment() {
		return nameOfMediumOfPayment;
	}

	public void setNameOfMediumOfPayment( String nameOfMediumOfPayment ) {
		this.nameOfMediumOfPayment = nameOfMediumOfPayment;
	}

	public String getSpecificationOrDescription() {
		return specificationOrDescription;
	}

	public void setSpecificationOrDescription( String specificationOrDescription ) {
		this.specificationOrDescription = specificationOrDescription;
	}

	@Override
	public String toString() {
		return "MediumOfPayment [id=" + id + ", nameOfMediumOfPayment=" + nameOfMediumOfPayment
				+ ", specificationOrDescription=" + specificationOrDescription + "]";
	}

}
