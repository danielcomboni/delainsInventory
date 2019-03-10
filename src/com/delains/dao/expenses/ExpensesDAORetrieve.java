package com.delains.dao.expenses;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.delains.dao.payments.MediumOfPaymentHibernation;
import com.delains.dao.utils.DBUtils;
import com.delains.model.expenses.Expenses;
import com.delains.model.payments.MediumOfPayment;

public class ExpensesDAORetrieve {

	public static List < Expenses > findAllExpenses() {

		List < Expenses > purchases = new ArrayList <>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		Map < BigDecimal, MediumOfPayment > mapMediumOfPayment = MediumOfPaymentHibernation
				.mapOfMediumOfPaymentsToThierId();

		try {

			preparedStatement = connection.prepareStatement( DBUtils.getRetrievingCommandString( "expenses", "" ) );
			resultSet = preparedStatement.executeQuery();

			while ( resultSet.next() ) {

				BigDecimal id = resultSet.getBigDecimal( "id" );
				String date = resultSet.getString( "date" );
				BigDecimal amountSpent = resultSet.getBigDecimal( "amount_spent" );
				String reason = resultSet.getString( "reason" );

				BigDecimal mediumOfPaymentId = resultSet.getBigDecimal( "medium_of_payment_id" );

				Expenses expenses = new Expenses();
				expenses.setId( id );
				expenses.setDate( date );
				expenses.setReason( reason );
				expenses.setAmountSpent( amountSpent );
				expenses.setMediumOfPaymentId( mapMediumOfPayment.get( mediumOfPaymentId ) );

				purchases.add( expenses );

			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, resultSet );
		}
		return purchases;
	}

}
