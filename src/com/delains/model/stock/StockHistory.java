package com.delains.model.stock;

import java.math.BigDecimal;

public class StockHistory {

	private BigDecimal id;
	private Stock stockId;
	private BigDecimal stockQuantity;
	private String date;

	public StockHistory() {
		// TODO Auto-generated constructor stub
	}

	public StockHistory(BigDecimal id, Stock stockId, BigDecimal stockQuantity, String date) {
		super();
		this.id = id;
		this.stockId = stockId;
		this.stockQuantity = stockQuantity;
		this.date = date;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public Stock getStockId() {
		return stockId;
	}

	public void setStockId(Stock stockId) {
		this.stockId = stockId;
	}

	public BigDecimal getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(BigDecimal stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "StockHistory [id=" + id + ", stockId=" + stockId + ", stockQuantity=" + stockQuantity + ", date=" + date
				+ "]";
	}

}
