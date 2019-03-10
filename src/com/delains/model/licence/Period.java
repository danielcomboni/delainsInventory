package com.delains.model.licence;

import java.math.BigDecimal;

public class Period {

	private BigDecimal id;
	private int numberOfDays;

	public BigDecimal getId() {
		return id;
	}

	public void setId( BigDecimal id ) {
		this.id = id;
	}

	public int getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays( int numberOfDays ) {
		this.numberOfDays = numberOfDays;
	}

	@Override
	public String toString() {
		return "Period [id=" + id + ", numberOfDays=" + numberOfDays + "]";
	}

}
