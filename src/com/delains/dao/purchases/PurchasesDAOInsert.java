package com.delains.dao.purchases;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.purchases.Purchase;

public class PurchasesDAOInsert {

	private static LinkedHashMap < String, String > purchasesInsertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "item_id", "INTEGER" );
		map.put( "quantity", "DECIMAL(50,5)" );
		map.put( "price", "DECIMAL(50,5)" );
		map.put( "total_cost", "DECIMAL(50,5)" );
		map.put( "supplier_id", "INTEGER" );
		map.put( "amount_paid", "DECIMAL(50,5)" );
		map.put( "balance", "DECIMAL(50,5)" );
		map.put( "discount_received", "DECIMAL(50,5)" );
		map.put( "is_credit", "boolean" );
		map.put( "medium_of_payment_id", "INTEGER" );

		return map;
	}

	public static Purchase newPurchase(Purchase purchase ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( purchasesInsertionDefinition(), "purchases" ) );

			/*
			 * 
			 * total cost when no discount is recorded
			 * 
			 */
			BigDecimal totalCostWithoutDiscountReceived = purchase.getPrice()
					.multiply( purchase.getQuantityPurchased() );
			BigDecimal amountPaid = purchase.getAmountPaid();

			if ( totalCostWithoutDiscountReceived.doubleValue() == amountPaid.doubleValue() ) {
				purchase.setAmountPaid( amountPaid );
				purchase.setBalanceToBeCleared( BigDecimal.ZERO );
				purchase.setDiscountReceived( BigDecimal.ZERO );
			}

			if ( totalCostWithoutDiscountReceived.doubleValue() > amountPaid.doubleValue() ) {
				purchase.setBalanceToBeCleared( totalCostWithoutDiscountReceived.subtract( amountPaid ) );
			}

			BigDecimal discount = purchase.getDiscountReceived();

			BigDecimal totalCostWithDiscount = totalCostWithoutDiscountReceived.subtract( discount );
			BigDecimal amountPaidWithDiscount = purchase.getAmountPaid();

			if ( totalCostWithDiscount.add( discount ).doubleValue() == totalCostWithoutDiscountReceived
					.doubleValue() ) {

				if ( amountPaidWithDiscount.add( discount ).doubleValue() == totalCostWithoutDiscountReceived
						.doubleValue() ) {

					purchase.setBalanceToBeCleared( BigDecimal.ZERO );
					purchase.setAmountPaid( amountPaidWithDiscount );

				}

				else {

					BigDecimal balance = totalCostWithDiscount.subtract( amountPaid );
					purchase.setBalanceToBeCleared( balance );

				}

			}

			BigDecimal supplierId = BigDecimal.ZERO;
			if ( purchase.getSupplierId() == null ) {
				supplierId = BigDecimal.ZERO;
			} else {
				supplierId = purchase.getSupplierId().getId();
			}

			BigDecimal mediumOfPaymendId = BigDecimal.ZERO;
			if ( purchase.getMediumOfPaymentId() != null ) {
				mediumOfPaymendId = BigDecimal.ZERO;
			} else {
				mediumOfPaymendId = purchase.getMediumOfPaymentId().getId();
			}

			preparedStatement.setString( 1, purchase.getDate() );
			preparedStatement.setBigDecimal( 2, purchase.getItemId().getId() );
			preparedStatement.setBigDecimal( 3, purchase.getQuantityPurchased() );
			preparedStatement.setBigDecimal( 4, purchase.getPrice() );
			preparedStatement.setBigDecimal( 5, purchase.getTotalCost() );
			preparedStatement.setBigDecimal( 6, supplierId );
			preparedStatement.setBigDecimal( 7, purchase.getAmountPaid() );
			preparedStatement.setBigDecimal( 8, purchase.getBalanceToBeCleared() );
			preparedStatement.setBigDecimal( 9, purchase.getDiscountReceived() );
			preparedStatement.setBoolean( 10, purchase.isCredit() );
			preparedStatement.setBigDecimal( 11, mediumOfPaymendId );

			preparedStatement.executeUpdate();

			BigDecimal id;
			ResultSet rs = preparedStatement.getGeneratedKeys();

			if ( rs != null && rs.next() ) {
				id = rs.getBigDecimal( 1 );
				purchase.setId(id);
			}

			rs.close();


		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
        return purchase;
    }

}
