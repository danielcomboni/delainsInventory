package com.delains.dao.stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.stock.StockWarningPoint;

public class StockWarningPointDAOInsert {

	private static LinkedHashMap < String, String > itemInsertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "item_id", "INTEGER UNIQUE NOT NULL" );
		map.put( "quantity_limit", "DECIMAL(50,5)" );

		return map;
	}

	public static void newStockWarnigF( StockWarningPoint warningPoint ) {

		System.out.println( "warning; " + warningPoint );

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( itemInsertionDefinition(), "stock_warning" ) );

			preparedStatement.setBigDecimal( 1, warningPoint.getItemId().getId() );
			preparedStatement.setBigDecimal( 2, warningPoint.getQuantityLimit() );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
