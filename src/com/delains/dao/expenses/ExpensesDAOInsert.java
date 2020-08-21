package com.delains.dao.expenses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.expenses.Expenses;
import com.delains.utilities.CurrentTimestamp;

public class ExpensesDAOInsert {


	private static LinkedHashMap < String, String > insertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "amount_spent", "DECIMAL(50,5)" );
		map.put( "reason", "LONGTEXT" );
		map.put( "medium_of_payment_id", "INTEGER" );

		return map;
	}

	public static void newExpense( Expenses expenses ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( insertionDefinition(), "expenses" ) );

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

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
