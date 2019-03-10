package com.delains.dao.pos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.sales.SalesReturn;
import com.delains.utilities.CurrentTimestamp;

public class SalesReturnDAOInsertion {

	private static LinkedHashMap < String, String > insertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "item_id", "INTEGER NOT NULL" );
		map.put( "quantity_returned", "DECIMAL(50,5)" );
		map.put( "quantity_restocked", "DECIMAL(50,5)" );
		map.put( "quantity_discarded", "DECIMAL(50,5)" );
		map.put( "customer_id", "INTEGER" );
		map.put( "pos_id", "INTEGER" );
		map.put( "reason", "longtext" );

		return map;
	}

	public static void newSalesReturn( SalesReturn salesReturn ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( insertionDefinition(), "sales_return" ) );

			BigDecimal customerId = BigDecimal.ZERO;
			if ( salesReturn.getCustomerId() != null ) {
				customerId = salesReturn.getCustomerId().getId();
			}

			preparedStatement.setString( 1, CurrentTimestamp.getDateTimeEndAtMinutes() );
			preparedStatement.setBigDecimal( 2, salesReturn.getItemId().getId() );
			preparedStatement.setBigDecimal( 3, salesReturn.getQuantityReturned() );
			preparedStatement.setBigDecimal( 4, salesReturn.getQuantityReStocked() );
			preparedStatement.setBigDecimal( 5, salesReturn.getQuantityDiscarded() );
			preparedStatement.setBigDecimal( 6, customerId );
			preparedStatement.setBigDecimal( 7, salesReturn.getPosId().getId() );
			preparedStatement.setString( 8, salesReturn.getReason() );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
