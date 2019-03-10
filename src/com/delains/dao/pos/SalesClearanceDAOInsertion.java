package com.delains.dao.pos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.sales.SalesClearance;

public class SalesClearanceDAOInsertion {

	private static LinkedHashMap < String, String > insertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "pos_id", "INTEGER" );
		map.put( "amount_cleared", "DECIMAL(50,5)" );
		map.put( "balance", "DECIMAL(50,5)" );

		return map;
	}

	public static void newSalesClearance( SalesClearance clearance ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( insertionDefinition(), "sales_clearance" ) );

			preparedStatement.setString( 1, clearance.getDate() );
			preparedStatement.setBigDecimal( 2, clearance.getPosId().getId() );
			preparedStatement.setBigDecimal( 3, clearance.getAmountCleared() );
			preparedStatement.setBigDecimal( 4, clearance.getBalanceToBeCleared() );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
