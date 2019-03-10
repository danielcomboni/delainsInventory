package com.delains.model.licence;

import java.math.BigDecimal;

public class RandomNumber {

	private BigDecimal id;
	private BigDecimal randNum;

	public BigDecimal getId() {
		return id;
	}

	public void setId( BigDecimal id ) {
		this.id = id;
	}

	public BigDecimal getRandNum() {
		return randNum;
	}

	public void setRandNum( BigDecimal randNum ) {
		this.randNum = randNum;
	}

	@Override
	public String toString() {
		return "RandomNumber [id=" + id + ", randNum=" + randNum + "]";
	}

}
