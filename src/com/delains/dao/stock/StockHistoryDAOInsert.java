package com.delains.dao.stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.stock.StockHistory;
import com.delains.utilities.CurrentTimestamp;

public class StockHistoryDAOInsert {

	private static LinkedHashMap < String, String > customerInsertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "stock_id", "INTEGER" );
		map.put( "stock_quantity", "DECIMAL(50,5)" );
		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );

		return map;
	}

	public static void newStockHistory( StockHistory history ) {

		StockHistoryDAO.createTableStockHistory();
		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getInsertCommandString( customerInsertionDefinition(), "stock_history" ) );

			preparedStatement.setBigDecimal( 1, history.getStockId().getId() );
			preparedStatement.setBigDecimal( 2, history.getStockQuantity() );
			preparedStatement.setString( 3, CurrentTimestamp.getDateTimeEndAtSecond() );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
