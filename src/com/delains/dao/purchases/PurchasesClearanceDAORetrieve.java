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
import com.delains.model.purchases.Purchase;
import com.delains.model.purchases.PurchaseClearance;

public class PurchasesClearanceDAORetrieve {

	public static List < PurchaseClearance > findAllPurchases() {

		List < PurchaseClearance > purchases = new ArrayList <>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getRetrievingCommandString( "purchases_clearance", "" ) );
			resultSet = preparedStatement.executeQuery();

			Map < BigDecimal, Purchase > mapPurchase = PurchasesHibernation.mapOfPurchasesToThierId();

			while ( resultSet.next() ) {

				BigDecimal id = resultSet.getBigDecimal( "id" );
				String date = resultSet.getString( "date" );
				BigDecimal purchaseId = resultSet.getBigDecimal( "purchase_id" );
				BigDecimal amountCleared = resultSet.getBigDecimal( "amount_cleared" );
				BigDecimal balance = resultSet.getBigDecimal( "balance" );

				Purchase p = mapPurchase.get( purchaseId );

				PurchaseClearance pc = new PurchaseClearance();
				pc.setDate( date );
				pc.setAmountCleared( amountCleared );
				pc.setBalanceToBeCleared( balance );
				pc.setPurchaseId( p );
				pc.setId( id );

				purchases.add( pc );

			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, resultSet );
		}
		return purchases;
	}

}
