package com.delains.dao.stock;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.stock.StockWarningPoint;

public class StockWarningPointDAOUpdate {

	private static LinkedHashMap < String, String > mapsForUpdatingItems() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "quantity_limit", "quantity_limit" );

		return map;

	}

	public static void updateStockWarning( StockWarningPoint item, BigDecimal itemId ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getUpdateCommandString( "stock_warning", mapsForUpdatingItems(), "WHERE item_id=?" ) );

			preparedStatement.setBigDecimal( 1, item.getQuantityLimit() );
			preparedStatement.setBigDecimal( 2, item.getItemId().getId() );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
