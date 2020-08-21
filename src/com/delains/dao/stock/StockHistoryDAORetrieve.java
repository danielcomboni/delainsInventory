package com.delains.dao.stock;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.delains.dao.utils.DBUtils;
import com.delains.model.stock.Stock;
import com.delains.model.stock.StockHistory;

public class StockHistoryDAORetrieve {

	public static List<StockHistory> findAllStockHistory() {

		List<StockHistory> stockHistories = new ArrayList<>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection.prepareStatement(DBUtils.getRetrievingCommandString("stock_history", ""));
			resultSet = preparedStatement.executeQuery();

			Map<BigDecimal, Stock> map = StockHibernation.mapOfStockToThierId();

			while (resultSet.next()) {

				BigDecimal id = resultSet.getBigDecimal("id");
				String date = resultSet.getString("date");
				BigDecimal stockQuantity = resultSet.getBigDecimal("stock_quantity");
				BigDecimal stockId = resultSet.getBigDecimal("stock_id");
				Stock stock = map.get(stockId);

				StockHistory stockHistory = new StockHistory(id, stock, stockQuantity, date);

				stockHistories.add(stockHistory);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, resultSet);
		}
		return stockHistories;
	}

}
