package com.delains.dao.stock;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Item;
import com.delains.model.stock.Stock;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class StockDAO {

	private static LinkedHashMap<String, String> usersTableDefinitions() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("id", "INTEGER PRIMARY KEY AUTOINCREMENT");
		map.put("item_id", "INTEGER UNIQUE NOT NULL");
		map.put("item_quantity", "DECIMAL(50,5)");
		map.put("date", "DATE DEFAULT (datetime('now','localtime'))");

		return map;
	}

	private static void createTableUsers() {
		DBUtils.apiToCreateTable(usersTableDefinitions(), "stock");
	}

	public static void newStock(Stock stock) {
		createTableUsers();
		StockDAOInsert.newStock(stock);
	}

	public static void reduceStockOnSale(ObservableMap<Item, Stock> map) {
		StockDAOUpdate.reduceStock(map);
	}

	public static void updateStock(Stock stock, BigDecimal idOfStock) {
		StockDAOUpdate.updateStock(stock, idOfStock);
	}

	public static void deleteStock(BigDecimal id) {
		StockDAODelete.deleteStock(id);
	}

	public static List<Stock> findAllStocks() {
		createTableUsers();
		return StockDAORetrieve.findAllStocks();
	}

	public static ObservableList<Stock> changeListUtilToListObservable() {

		ObservableList<Stock> observableList = FXCollections.observableArrayList();

		for (int i = 0; i < findAllStocks().size(); i++) {
			Stock u = findAllStocks().get(i);
			observableList.add(u);
		}

		return observableList;
	}

}
