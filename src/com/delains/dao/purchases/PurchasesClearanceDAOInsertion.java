package com.delains.dao.purchases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.purchases.PurchaseClearance;

public class PurchasesClearanceDAOInsertion {

	private static LinkedHashMap < String, String > insertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))");
		map.put( "purchase_id", "INTEGER" );
		map.put( "amount_cleared", "DECIMAL(50,5)" );
		map.put( "balance", "DECIMAL(50,5)" );

		return map;
	}

	public static void newPurchaseSClearance( PurchaseClearance clearance ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( insertionDefinition(), "purchases_clearance" ) );

			if ( clearance.getDate() == null ) {
				Timestamp timestamp = Timestamp.valueOf( LocalDateTime.now() );
				clearance.setDate( timestamp.toString() );
			}

			preparedStatement.setString( 1, clearance.getDate() );
			preparedStatement.setBigDecimal( 2, clearance.getPurchaseId().getId() );
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
