package com.delains.dao.deposits;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.delains.dao.customers.CustomerHibernation;
import com.delains.dao.payments.MediumOfPaymentHibernation;
import com.delains.dao.suppliers.SupplierHibernation;
import com.delains.dao.utils.DBUtils;
import com.delains.model.customers.Customer;
import com.delains.model.deposits.Deposit;
import com.delains.model.payments.MediumOfPayment;
import com.delains.model.suppliers.Supplier;

public class DepositDAORetrieve {

	public static List < Deposit > findAllDeposit() {

		List < Deposit > purchases = new ArrayList <>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		Map < BigDecimal, MediumOfPayment > mapMediumOfPayment = MediumOfPaymentHibernation
				.mapOfMediumOfPaymentsToThierId();

		Map < BigDecimal, Customer > map = CustomerHibernation.mapOfCustomersToThierId();

		try {

			preparedStatement = connection.prepareStatement( DBUtils.getRetrievingCommandString( "deposits", "" ) );
			resultSet = preparedStatement.executeQuery();

			while ( resultSet.next() ) {

				BigDecimal id = resultSet.getBigDecimal( "id" );
				String date = resultSet.getString( "date" );
				BigDecimal amountDeposited = resultSet.getBigDecimal( "amount_deposited" );
				String reason = resultSet.getString( "reason" );
				BigDecimal mediumOfPaymentFromId = resultSet.getBigDecimal( "medium_of_payment_from_id" );
				BigDecimal mediumOfPaymentToId = resultSet.getBigDecimal( "medium_of_payment_to_id" );
				BigDecimal supplierClearedId = resultSet.getBigDecimal( "supplier_cleared_id" );

				Deposit d = new Deposit();

				d.setId( id );
				d.setDate( date );
				d.setAmountDeposited( amountDeposited );
				d.setMediumOfPaymentFromId( mapMediumOfPayment.get( mediumOfPaymentFromId ) );
				d.setMediumOfPaymentToId( mapMediumOfPayment.get( mediumOfPaymentToId ) );
				d.setCustomerId( map.get( supplierClearedId ) );
				d.setReason( reason );

				purchases.add( d );

			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, resultSet );
		}
		return purchases;
	}

}
