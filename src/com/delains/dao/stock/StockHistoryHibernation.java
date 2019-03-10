package com.delains.dao.stock;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.model.stock.StockHistory;

import javafx.collections.ObservableList;

public class StockHistoryHibernation {

	private static ObservableList<StockHistory> histories;

	public static ObservableList<StockHistory> getHistories() {
		return histories;
	}

	public static void setHistories(ObservableList<StockHistory> histories) {
		StockHistoryHibernation.histories = histories;
	}

	public static ObservableList<StockHistory> findAllStockHistorysObservableList() {
		if (getHistories() == null) {
			setHistories(StockHistoryDAO.changeListUtilToListObservable());
		}
		return getHistories();
	}

	public static ObservableList<StockHistory> findAllStockHistorysObservableListRefreshed() {
		setHistories(StockHistoryDAO.changeListUtilToListObservable());
		return getHistories();
	}

	public static Map<BigDecimal, StockHistory> mapOfStockHistorysToThierId() {
		Map<BigDecimal, StockHistory> map = new LinkedHashMap<>();
		for (int i = 0; i < findAllStockHistorysObservableList().size(); i++) {
			StockHistory type = findAllStockHistorysObservableList().get(i);
			map.put(type.getId(), type);
		}
		return map;
	}

}
