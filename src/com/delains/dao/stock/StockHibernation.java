package com.delains.dao.stock;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.model.items.Item;
import com.delains.model.stock.Stock;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class StockHibernation {

	private static ObservableList<Stock> stocks;

	public static ObservableList<Stock> getStocks() {
		return stocks;
	}

	public static void setStocks(ObservableList<Stock> stocks) {
		StockHibernation.stocks = stocks;
	}

	public static void newStock(Stock stock) {
		StockDAO.newStock(stock);
	}

	public static void updateStock(Stock stock, BigDecimal idOfStock) {
		StockDAO.updateStock(stock, idOfStock);
	}

	public static void reducestockOnSale(ObservableMap<Item, Stock> map) {
		StockDAO.reduceStockOnSale(map);
	}

	public static void deleteStock(BigDecimal id) {
		StockDAO.deleteStock(id);
	}

	public static ObservableList<Stock> findAllStockObservableList() {
		if (getStocks() == null) {
			setStocks(StockDAO.changeListUtilToListObservable());
		}
		return getStocks();
	}

	public static ObservableList<Stock> findAllStockObservableListRefreshed() {
		setStocks(StockDAO.changeListUtilToListObservable());
		return getStocks();
	}

	public static Map<BigDecimal, Stock> mapOfStockToThierId() {
		Map<BigDecimal, Stock> map = new LinkedHashMap<>();
		for (int i = 0; i < findAllStockObservableList().size(); i++) {
			Stock type = findAllStockObservableList().get(i);
			map.put(type.getId(), type);
		}
		return map;
	}

	public static Map<BigDecimal, Stock> mapOfStockToThierItemIDs() {
		Map<BigDecimal, Stock> map = new LinkedHashMap<>();
		for (int i = 0; i < findAllStockObservableList().size(); i++) {
			Stock type = findAllStockObservableList().get(i);
map.put(type.getItemId().getId(), type);
		}
		return map;
	}

}
