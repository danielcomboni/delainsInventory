package com.delains.dao.purchases;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.purchases.PurchaseReturn;
import com.delains.utilities.CurrentTimestamp;

public class PurchaseReturnDAOInsertion {

	private static LinkedHashMap < String, String > insertionDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "item_id", "INTEGER" );
		map.put( "quantity", "DECIMAL(50,5)" );
		map.put( "supplier_id", "INTEGER" );
		map.put( "reason", "LONGTEXT" );
		map.put( "purchase_id", "INTEGER" );

		return map;
	}

	public static void newPurchaseReturn( PurchaseReturn purchaseReturn ) {
		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( insertionDefinitions(), "purchases_return" ) );

			// Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
			// purchase.setDate(timestamp.toString());

			/*
			 * 
			 * total cost when no discount is recorded
			 * 
			 */

			if ( purchaseReturn.getDate() == null ) {
				Timestamp timestamp = Timestamp.valueOf( LocalDateTime.now() );
				purchaseReturn.setDate( timestamp.toString() );
			}

			BigDecimal supplierId = BigDecimal.ZERO;
			if ( purchaseReturn.getSupplierId() == null ) {
				supplierId = BigDecimal.ZERO;
			} else {
				supplierId = purchaseReturn.getSupplierId().getId();
			}

			preparedStatement.setString( 1, CurrentTimestamp.getDateTimeEndAtMinutes() );
			preparedStatement.setBigDecimal( 2, purchaseReturn.getItemId().getId() );
			preparedStatement.setBigDecimal( 3, purchaseReturn.getQuantity() );
			preparedStatement.setBigDecimal( 4, supplierId );
			preparedStatement.setString( 5, purchaseReturn.getReason() );
			preparedStatement.setBigDecimal( 6, purchaseReturn.getPurchaseId().getId() );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
