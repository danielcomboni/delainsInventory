package com.delains.dao.stock;

import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.stock.StockHistory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StockHistoryDAO {

	private static LinkedHashMap<String, String> stockHistoryTableDefinitions() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();

		map.put("id", "INTEGER PRIMARY KEY AUTOINCREMENT");
		map.put("stock_id", "INTEGER");
		map.put("stock_quantity", "DECIMAL(50,5)");
		map.put("date", "DATE DEFAULT (datetime('now','localtime'))");

		return map;

	}

	public static void createTableStockHistory() {
		DBUtils.apiToCreateTable(stockHistoryTableDefinitions(), "stock_history");
	}

	public static List<StockHistory> findAllStockHistory() {
		createTableStockHistory();
		return StockHistoryDAORetrieve.findAllStockHistory();
	}

	public static ObservableList<StockHistory> changeListUtilToListObservable() {

		ObservableList<StockHistory> observableList = FXCollections.observableArrayList();

		for (int i = 0; i < findAllStockHistory().size(); i++) {
			StockHistory u = findAllStockHistory().get(i);
			observableList.add(u);
		}

		return observableList;
	}

}
