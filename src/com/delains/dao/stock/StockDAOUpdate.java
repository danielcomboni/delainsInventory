package com.delains.dao.stock;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.delains.dao.item.ItemHibernation;
import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Item;
import com.delains.model.stock.Stock;
import com.delains.model.stock.StockHistory;

import javafx.collections.ObservableMap;

public class StockDAOUpdate {

	private static LinkedHashMap<String, String> mapsForUpdatingStock() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("item_id", "item_id");
		map.put("item_quantity", "item_quantity");
		map.put("date", "date");

		return map;

	}

	static void updateStock(Stock stock, BigDecimal idOfStock) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement(DBUtils.getUpdateCommandString("stock", mapsForUpdatingStock(), "WHERE id=?"));

			stock.setId(idOfStock);

			if (stock.getDate() == null) {
				Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
				stock.setDate(timestamp.toString());
			}

			preparedStatement.setBigDecimal(1, stock.getItemId().getId());
			preparedStatement.setBigDecimal(2, stock.getItemQuantity());
			preparedStatement.setString(3, stock.getDate());
			preparedStatement.setBigDecimal(4, stock.getId());

			StockHistory history = new StockHistory();
			history.setStockId(stock);
			history.setStockQuantity(stock.getItemQuantity());

			preparedStatement.executeUpdate();

			StockHistoryDAOInsert.newStockHistory(history);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, null);
		}
	}

	private static LinkedHashMap<String, String> mapsForUpdatingStock_REDUCE() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("item_quantity", "item_quantity");
		map.put("date", "date");

		return map;

	}

	static void reduceStock(ObservableMap<Item, Stock> map) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(
					DBUtils.getUpdateCommandString("stock", mapsForUpdatingStock_REDUCE(), "WHERE item_id=?"));

			Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

			preparedStatement.clearBatch();

			for (Entry<Item, Stock> s : map.entrySet()) {

				Stock stock;
				stock = s.getValue();

				stock.setDate(timestamp.toString());

				Stock stockPrevious = addingStock(stock.getItemId().getId());
				BigDecimal qtyPrevious = stockPrevious.getItemQuantity();
				BigDecimal qtyNew = stock.getItemQuantity();

				BigDecimal qtyRequired = qtyPrevious.subtract(qtyNew);

				stock.setItemQuantity(qtyRequired);
				stock.setItemId(s.getKey());

				preparedStatement.setBigDecimal(1, stock.getItemQuantity());
				preparedStatement.setString(2, stock.getDate());
				preparedStatement.setBigDecimal(3, stock.getItemId().getId());

				preparedStatement.addBatch();

			}

			preparedStatement.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, null);
		}
	}

	 public static Stock addingStock(BigDecimal itemId) {
		Stock earlierStock;
		earlierStock = new Stock();
		PreparedStatement preparedStatement;
		preparedStatement = null;
		ResultSet resultSet;
		resultSet = null;
		Connection connection;
		connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement(DBUtils.getRetrievingCommandString("stock", "WHERE item_id = ?"));
			preparedStatement.setBigDecimal(1, itemId);
			resultSet = preparedStatement.executeQuery();

			Map<BigDecimal, Item> map2 = ItemHibernation.mapOfItemsToThierId();

			if (resultSet.next()) {

				BigDecimal id = resultSet.getBigDecimal("id");
				// BigDecimal itemID = resultSet.getBigDecimal("item_id");
				BigDecimal itemQuantity = resultSet.getBigDecimal("item_quantity");
				String date = resultSet.getString("date");

				Item item = map2.get(itemId);

				earlierStock = new Stock(id, item, itemQuantity, date);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, resultSet);
		}
		return earlierStock;
	}

}
