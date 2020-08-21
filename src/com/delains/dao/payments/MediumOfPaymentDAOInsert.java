package com.delains.dao.payments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.payments.MediumOfPayment;

public class MediumOfPaymentDAOInsert {
	
	private static LinkedHashMap < String, String > insertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "name_of_medium_of_payment", "VARCHAR(255)" );
		map.put( "specification", "VARCHAR(255)" );

		return map;
	}

	public static void newMediumOfPayment( MediumOfPayment mediumOfPayment ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getInsertCommandString( insertionDefinition(), "medium_of_payment" ) );

			preparedStatement.setString( 1, mediumOfPayment.getNameOfMediumOfPayment() );
			preparedStatement.setString( 2, mediumOfPayment.getSpecificationOrDescription() );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
