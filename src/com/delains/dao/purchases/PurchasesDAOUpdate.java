package com.delains.dao.purchases;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.purchases.Purchase;

public class PurchasesDAOUpdate {

	private static LinkedHashMap < String, String > mapsForUpdatingPurchases() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "date", "date" );
		map.put( "item_id", "item_id" );
		map.put( "quantity", "quantity" );
		map.put( "price", "price" );
		map.put( "total_cost", "total_cost" );
		map.put( "is_credit", "is_credit" );
		map.put( "supplier_id", "supplier_id" );
		map.put( "amount_paid", "amount_paid" );
		map.put( "balance", "balance" );
		map.put( "discount_received", "discount_received" );
		map.put( "medium_of_payment_id", "medium_of_payment_id" );

		return map;

	}

	public static Purchase updatePurchase( Purchase purchase, BigDecimal idOfPurchase ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getUpdateCommandString( "purchases", mapsForUpdatingPurchases(), "WHERE id=?" ) );

			purchase.setId( idOfPurchase );

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
			preparedStatement.setBoolean( 6, purchase.isCredit() );

			BigDecimal supplierId = BigDecimal.ZERO;
			if ( purchase.getSupplierId() == null ) {
				supplierId = BigDecimal.ZERO;
			} else {
				supplierId = purchase.getSupplierId().getId();
			}

			preparedStatement.setBigDecimal( 7, supplierId );
			preparedStatement.setBigDecimal( 8, purchase.getAmountPaid() );
			preparedStatement.setBigDecimal( 9, purchase.getBalanceToBeCleared() );

			preparedStatement.setBigDecimal( 10, purchase.getDiscountReceived() );
			preparedStatement.setBigDecimal( 11, mediumOfPaymendId );
			preparedStatement.setBigDecimal( 12, purchase.getId() );

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
