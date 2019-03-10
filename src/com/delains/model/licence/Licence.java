package com.delains.model.licence;

import java.math.BigDecimal;

public class Licence {

	private BigDecimal id;
	private String fromDate;
	private String toDate;
	private BigDecimal period;
	private BigDecimal daysLeft;
	private RandomNumber randomNumberId;

	public BigDecimal getId() {
		return id;
	}

	public void setId( BigDecimal id ) {
		this.id = id;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate( String fromDate ) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate( String toDate ) {
		this.toDate = toDate;
	}

	public BigDecimal getPeriod() {
		return period;
	}

	public void setPeriod( BigDecimal period ) {
		this.period = period;
	}

	public BigDecimal getDaysLeft() {
		return daysLeft;
	}

	public void setDaysLeft( BigDecimal daysLeft ) {
		this.daysLeft = daysLeft;
	}

	public RandomNumber getRandomNumberId() {
		return randomNumberId;
	}

	public void setRandomNumberId( RandomNumber randomNumberId ) {
		this.randomNumberId = randomNumberId;
	}

	@Override
	public String toString() {
		return "Licence [id=" + id + ", fromDate=" + fromDate + ", toDate=" + toDate + ", period=" + period
				+ ", daysLeft=" + daysLeft + ", randomNumberId=" + randomNumberId + "]";
	}

}
