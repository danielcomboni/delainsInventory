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

import com.delains.dao.item.ItemHibernation;
import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Item;
import com.delains.model.stock.Stock;
import com.delains.model.stock.StockHistory;

public class StockDAOInsert {

	private static LinkedHashMap<String, String> itemInsertionDefinition() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();

		map.put("item_id", "INTEGER");
		map.put("item_quantity", "DECIMAL(50,5)");
		map.put("date", "DATE DEFAULT (datetime('now','localtime'))");

		return map;
	}

	public static void newStock(Stock stock) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getInsertCommandString(itemInsertionDefinition(), "stock"),
					PreparedStatement.RETURN_GENERATED_KEYS);

			// Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
			// stock.setDate(timestamp.toString());

			BigDecimal itemId = stock.getItemId().getId();

			if (!mappingStockToItsId().containsKey(itemId)) {

				preparedStatement.setBigDecimal(1, stock.getItemId().getId());
				preparedStatement.setBigDecimal(2, stock.getItemQuantity());
				preparedStatement.setString(3, stock.getDate());

				preparedStatement.executeUpdate();

resultSet = preparedStatement.getGeneratedKeys();

				BigDecimal id = BigDecimal.ZERO;

				if (resultSet != null && resultSet.next()) {
					id = resultSet.getBigDecimal(1);
				}

stock.setId(id);

				StockHistory history = new StockHistory();
				history.setStockQuantity(stock.getItemQuantity());
				history.setStockId(stock);

				StockHistoryDAOInsert.newStockHistory(history);

			}

			else {

				Stock earlierStock = addingStock(itemId);

				Stock newStock = new Stock();
				newStock = earlierStock;
				BigDecimal earlierQuantity = newStock.getItemQuantity();

				BigDecimal quantityToBeAdded = stock.getItemQuantity();

				BigDecimal newQuantity = quantityToBeAdded.add(earlierQuantity);

				newStock.setItemQuantity(newQuantity);

				updateStock(newStock, itemId);

				StockHistory history = new StockHistory();
				history.setStockId(newStock);
				history.setStockQuantity(quantityToBeAdded);

				StockHistoryDAOInsert.newStockHistory(history);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, null);
		}
	}

	private static LinkedHashMap<String, String> mapsForUpdatingStock() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("item_quantity", "item_quantity");
		map.put("date", "date");

		return map;

	}

	private static void updateStock(Stock stock, BigDecimal itemId) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getUpdateCommandString("stock", mapsForUpdatingStock(), "WHERE item_id=?"));

			Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
			stock.setDate(timestamp.toString());

			preparedStatement.setBigDecimal(1, stock.getItemQuantity());
			preparedStatement.setString(2, stock.getDate());
			preparedStatement.setBigDecimal(3, stock.getItemId().getId());
			preparedStatement.executeUpdate();

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, null);
		}
	}

	private static Map<BigDecimal, Stock> mappingStockToItsId() {
		Map<BigDecimal, Stock> map = new LinkedHashMap<>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(DBUtils.getRetrievingCommandString("stock", ""));
			resultSet = preparedStatement.executeQuery();

			Map<BigDecimal, Item> map2 = ItemHibernation.mapOfItemsToThierId();

			while (resultSet.next()) {

				BigDecimal id = resultSet.getBigDecimal("id");
				BigDecimal itemId = resultSet.getBigDecimal("item_id");
				BigDecimal itemQuantity = resultSet.getBigDecimal("item_quantity");
				String date = resultSet.getString("date");

				Item item = map2.get(itemId);

				Stock stock = new Stock(id, item, itemQuantity, date);

				map.put(itemId, stock);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, resultSet);
		}
		return map;
	}

	private static Stock addingStock(BigDecimal itemId) {
		Stock earlierStock = new Stock();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement(DBUtils.getRetrievingCommandString("stock", "WHERE item_id = ?"));
			preparedStatement.setBigDecimal(1, itemId);
			// preparedStatement.executeUpdate();
			resultSet = preparedStatement.executeQuery();

			Map<BigDecimal, Item> map2 = ItemHibernation.mapOfItemsToThierId();

			if (resultSet.next()) {

				BigDecimal id = resultSet.getBigDecimal("id");
				// BigDecimal itemID = resultSet.getBigDecimal("item_id");
				BigDecimal itemQuantity = resultSet.getBigDecimal("item_quantity");
				String date = resultSet.getString("date");

				Item item = map2.get(itemId);

				Stock stock = new Stock(id, item, itemQuantity, date);

				earlierStock = stock;

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, resultSet);
		}
		return earlierStock;
	}

}
