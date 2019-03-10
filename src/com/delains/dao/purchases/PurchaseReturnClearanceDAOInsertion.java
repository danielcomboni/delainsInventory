package com.delains.dao.purchases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.purchases.PurchaseReturnClearance;
import com.delains.utilities.CurrentTimestamp;

public class PurchaseReturnClearanceDAOInsertion {

	private static LinkedHashMap < String, String > insertionDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "amount_returned", "DECIMAL(50,5)" );
		map.put( "purchase_return_id", "INTEGER" );

		return map;
	}

	public static void newPurchaseReturnClearance( PurchaseReturnClearance clearance ) {
		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getInsertCommandString( insertionDefinitions(), "purchases_return_clearance" ) );

			preparedStatement.setString( 1, CurrentTimestamp.getDateTimeEndAtMinutes() );
			preparedStatement.setBigDecimal( 2, clearance.getAmountPaid() );
			preparedStatement.setBigDecimal( 3, clearance.getPurchaseReturnId().getId() );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
