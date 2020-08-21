package com.delains.dao.payments;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.payments.MediumOfPayment;

public class MediumOfPaymentDAORetrieve {
	public static List < MediumOfPayment > findAllMediumsOfPayment() {

		List < MediumOfPayment > purchases = new ArrayList <>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getRetrievingCommandString( "medium_of_payment", "" ) );
			resultSet = preparedStatement.executeQuery();

			while ( resultSet.next() ) {

				BigDecimal id = resultSet.getBigDecimal( "id" );
				String nameOfMediumOfPayment = resultSet.getString( "name_of_medium_of_payment" );
				String specification = resultSet.getString( "specification" );

				MediumOfPayment mediumOfPayment = new MediumOfPayment();
				mediumOfPayment.setId( id );
				mediumOfPayment.setNameOfMediumOfPayment( nameOfMediumOfPayment );
				mediumOfPayment.setSpecificationOrDescription( specification );

				purchases.add( mediumOfPayment );

			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, resultSet );
		}
		return purchases;
	}

}
