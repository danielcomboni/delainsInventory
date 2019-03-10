package com.delains.dao.deposits;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.deposits.Deposit;
import com.delains.utilities.CurrentTimestamp;

public class DepositDAOInsert {

	private static LinkedHashMap < String, String > insertionDefinition() {
		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "amount_deposited", "DECIMAL(50,5)" );
		map.put( "medium_of_payment_from_id", "INTEGER" );
		map.put( "medium_of_payment_to_id", "INTEGER" );
		map.put( "supplier_cleared_id", "INTEGER" );
		map.put( "reason", "LONGTEXT" );
		return map;
	}

	public static void newDeposit( Deposit deposit ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( insertionDefinition(), "deposits" ) );

			String date = null;
			if ( deposit.getDate() != null ) {
				date = deposit.getDate();
			} else {
				date = CurrentTimestamp.getDateTimeEndAtMinutes();
			}

			BigDecimal mediumOfPaymentFromId = BigDecimal.ZERO;
			if ( deposit.getMediumOfPaymentFromId() != null ) {
				mediumOfPaymentFromId = deposit.getMediumOfPaymentFromId().getId();
			} else {
				mediumOfPaymentFromId = BigDecimal.ZERO;
			}

			BigDecimal mediumOfPaymentToId = BigDecimal.ZERO;
			if ( deposit.getMediumOfPaymentToId() != null ) {
				mediumOfPaymentToId = deposit.getMediumOfPaymentToId().getId();
			} else {
				mediumOfPaymentToId = BigDecimal.ZERO;
			}

			BigDecimal supplierClearedId = BigDecimal.ZERO;
			if ( deposit.getCustomerId() != null ) {
				supplierClearedId = deposit.getCustomerId().getId();
			} else {
				supplierClearedId = BigDecimal.ZERO;
			}

			preparedStatement.setString( 1, date );
			preparedStatement.setBigDecimal( 2, deposit.getAmountDeposited() );
			preparedStatement.setBigDecimal( 3, mediumOfPaymentFromId );
			preparedStatement.setBigDecimal( 4, mediumOfPaymentToId );
			preparedStatement.setBigDecimal( 5, supplierClearedId );
			preparedStatement.setString( 6, deposit.getReason() );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
