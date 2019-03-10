package com.delains.dao.deposits;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.deposits.Deposit;
import com.delains.utilities.CurrentTimestamp;

public class DepositDAOUpdate {

	private static LinkedHashMap < String, String > mapsForUpdatingPurchases() {
		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "date", "date" );
		map.put( "amount_deposited", "amount_deposited" );
		map.put( "medium_of_payment_from_id", "medium_of_payment_from_id" );
		map.put( "medium_of_payment_to_id", "medium_of_payment_to_id" );
		map.put( "supplier_cleared_id", "supplier_cleared_id" );
		map.put( "reason", "reason" );
		return map;
	}

	public static void updateDeposit( Deposit deposit, BigDecimal idOfDeposit ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getUpdateCommandString( "deposits", mapsForUpdatingPurchases(), "where id=?" ) );

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
			preparedStatement.setBigDecimal( 7, idOfDeposit );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
