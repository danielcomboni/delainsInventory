package com.delains.dao.payments;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.payments.MediumOfPayment;

public class MediumOfPaymentDAOUpdate {

	private static LinkedHashMap < String, String > mapsForUpdatingPurchases() {
		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "name_of_medium_of_payment", "name_of_medium_of_payment" );
		map.put( "specification", "specification" );
		return map;
	}

	public static void updateMediumOfPayment( MediumOfPayment mediumOfPayment, BigDecimal idOfMediumOfPayment ) {
		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		try {
			preparedStatement = connection.prepareStatement(
					DBUtils.getUpdateCommandString( "medium_of_payment", mapsForUpdatingPurchases(), "WHERE id=?" ) );
			mediumOfPayment.setId( idOfMediumOfPayment );
			preparedStatement.setString( 1, mediumOfPayment.getNameOfMediumOfPayment() );
			preparedStatement.setString( 2, mediumOfPayment.getSpecificationOrDescription() );
			preparedStatement.setBigDecimal( 3, mediumOfPayment.getId() );
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
