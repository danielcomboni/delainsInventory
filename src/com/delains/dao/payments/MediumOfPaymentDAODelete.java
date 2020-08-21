package com.delains.dao.payments;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.delains.dao.utils.DBUtils;

public class MediumOfPaymentDAODelete {

	public static void deleteMediumOfPayment( BigDecimal id ) {
		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getDeleteCommandString( "medium_of_payment", "WHERE id=?" ) );

			preparedStatement.setBigDecimal( 1, id );
			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}
}
