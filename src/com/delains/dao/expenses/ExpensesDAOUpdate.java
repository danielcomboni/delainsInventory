package com.delains.dao.expenses;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.expenses.Expenses;
import com.delains.utilities.CurrentTimestamp;

public class ExpensesDAOUpdate {

	private static LinkedHashMap < String, String > mapsForUpdatingPurchases() {
		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "date", "date" );
		map.put( "amount_spent", "amount_spent" );
		map.put( "reason", "reason" );
		map.put( "medium_of_payment_id", "medium_of_payment_id" );

		return map;
	}

	public static void updateExpenses( Expenses expenses, BigDecimal idOfExpense ) {
		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		try {
			preparedStatement = connection.prepareStatement(
					DBUtils.getUpdateCommandString( "expenses", mapsForUpdatingPurchases(), "WHERE id=?" ) );

			expenses.setId( idOfExpense );

			String date = null;
			if ( expenses.getDate() != null ) {
				date = expenses.getDate();
			} else {
				date = CurrentTimestamp.getDateTimeEndAtMinutes();
			}

			preparedStatement.setString( 1, date );
			preparedStatement.setBigDecimal( 2, expenses.getAmountSpent() );
			preparedStatement.setString( 3, expenses.getReason() );
			preparedStatement.setBigDecimal( 4, expenses.getMediumOfPaymentId().getId() );
			preparedStatement.setBigDecimal( 5, expenses.getId() );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
