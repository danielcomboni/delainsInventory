package com.delains.dao.purchases;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.delains.dao.utils.DBUtils;
import com.delains.model.purchases.PurchaseReturn;
import com.delains.model.purchases.PurchaseReturnClearance;

public class PurchaseReturnClearanceDAORetieve {

	public static List < PurchaseReturnClearance > findAllPurchaseReturnClearances() {

		List < PurchaseReturnClearance > purchases = new ArrayList <>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getRetrievingCommandString( "purchases_return_clearance", "" ) );
			resultSet = preparedStatement.executeQuery();

			Map < BigDecimal, PurchaseReturn > mapPurchaseReturn = PurchaseReturnHibernation
					.mapOfPurchaseReturnsToThierId();

			while ( resultSet.next() ) {

				BigDecimal id = resultSet.getBigDecimal( "id" );
				String date = resultSet.getString( "date" );
				BigDecimal amountPaid = resultSet.getBigDecimal( "amount_returned" );

				BigDecimal purchaseReturnId = resultSet.getBigDecimal( "purchase_return_id" );

				PurchaseReturnClearance returnClearance = new PurchaseReturnClearance();

				returnClearance.setAmountPaid( amountPaid );
				returnClearance.setId( id );
				returnClearance.setDate( date );
				returnClearance.setPurchaseReturnId( mapPurchaseReturn.get( purchaseReturnId ) );

				purchases.add( returnClearance );

			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, resultSet );
		}
		return purchases;
	}

}
