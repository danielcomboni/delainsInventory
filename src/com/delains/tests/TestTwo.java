package com.delains.tests;

import java.math.BigDecimal;

public class TestTwo {

	private BigDecimal id;
	private String name;

	public BigDecimal getId() {
		return id;
	}

	public void setId( BigDecimal id ) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TestTwo [id=" + id + ", name=" + name + "]";
	}

}
