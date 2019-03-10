package com.delains.dao.purchases;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.delains.dao.pos.POSHibernation;
import com.delains.dao.utils.DBUtils;
import com.delains.model.pos.POS;
import com.delains.model.sales.SalesClearance;

public class SalesClearanceDAORetrieve {

	public static List < SalesClearance > findAllPurchases() {

		List < SalesClearance > purchases = new ArrayList <>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getRetrievingCommandString( "sales_clearance", "ORDER BY pos_id" ) );
			resultSet = preparedStatement.executeQuery();

			// Map < BigDecimal, Item > mapItem = ItemHibernation.mapOfItemsToThierId();
			// Map < BigDecimal, Supplier > mapSupplier =
			// SupplierHibernation.mapOfSuppliersToThierId();
			POSHibernation.setMapPOSToItsID( null );
			Map < BigDecimal, POS > mapPurchase = POSHibernation.getMapPOSToID();

			while ( resultSet.next() ) {

				BigDecimal id = resultSet.getBigDecimal( "id" );
				String date = resultSet.getString( "date" );
				BigDecimal posId = resultSet.getBigDecimal( "pos_id" );
				BigDecimal amountCleared = resultSet.getBigDecimal( "amount_cleared" );
				BigDecimal balance = resultSet.getBigDecimal( "balance" );

				POS p = mapPurchase.get( posId );

				SalesClearance pc = new SalesClearance();
				pc.setDate( date );
				pc.setAmountCleared( amountCleared );
				pc.setBalanceToBeCleared( balance );
				pc.setPosId( p );
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
