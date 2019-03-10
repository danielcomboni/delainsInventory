package com.delains.dao.pos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Item;
import com.delains.model.pos.POS;
import com.delains.utilities.CurrentTimestamp;

import javafx.collections.ObservableMap;

public class POSDAOInsert {

	private static LinkedHashMap < String, String > posInsertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );// 1
		map.put( "item_id", "INTEGER NOT NULL" );// 2
		map.put( "pricing_id", "INTEGER NOT NULL" );// 3
		map.put( "price", "DECIMAL(50,5)" );// 4
		map.put( "quantity", "DECIMAL(50,5)" );// 5
		map.put( "cost", "DECIMAL(50,5)" );// 6
		map.put( "total_cost", "DECIMAL(50,5)" );// 7
		map.put( "amount_paid", "DECIMAL(50,5)" );// 8
		map.put( "credit", "boolean" );// 9
		map.put( "discount_allowed", "DECIMAL(50,2)" );// 10
		map.put( "customer_id", "INTEGER" );// 11
		map.put( "change", "DECIMAL(50,5)" );// 12
		map.put( "balance", "DECIMAL(50,5)" );// 13
		map.put( "medium_of_payment_id", "INTEGER" );
		return map;

	}

	public static void newPOS( ObservableMap < Item, POS > map ) {

		DBUtils.makeSureConnectionIsClosed();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			connection.setAutoCommit( false );

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( posInsertionDefinition(), "pos" ) );

			preparedStatement.clearBatch();

			BigDecimal mediumOfPaymentId = BigDecimal.ZERO;

			for ( Entry < Item, POS > p : map.entrySet() ) {

				POS pos = new POS();

				POS v = p.getValue();

				pos.setDate( CurrentTimestamp.getDateTimeEndAtSecond() );
				pos.setItemId( p.getKey() );
				pos.setPricing( v.getPricing() );
				pos.setPrice( v.getPrice() );
				pos.setQuantity( v.getQuantity() );
				pos.setCost( v.getCost() );
				pos.setTotalCost( v.getTotalCost() );
				pos.setAmountPaid( v.getAmountPaid() );
				pos.setCredit( v.isCredit() );

				if ( v.getMediumOfPaymentId() != null ) {
					pos.setMediumOfPaymentId( v.getMediumOfPaymentId() );
					mediumOfPaymentId = pos.getMediumOfPaymentId().getId();
				} else {
					mediumOfPaymentId = BigDecimal.ZERO;
				}

				BigDecimal discount = BigDecimal.ZERO;
				if ( v.getDiscountAllowed() == null ) {
					discount = BigDecimal.ZERO;
					pos.setDiscountAllowed( discount );
				} else {
					pos.setDiscountAllowed( v.getDiscountAllowed() );
				}

				BigDecimal customerId = BigDecimal.ZERO;

				if ( v.getCustomerId() == null ) {
					customerId = BigDecimal.ZERO;
				} else {
					customerId = v.getCustomerId().getId();
				}

				pos.setCustomerId( v.getCustomerId() );
				pos.setChange( v.getChange() );
				pos.setBalanceToBePaidByCustomer( v.getBalanceToBePaidByCustomer() );

				preparedStatement.setString( 1, pos.getDate() );
				preparedStatement.setBigDecimal( 2, pos.getItemId().getId() );
				preparedStatement.setBigDecimal( 3, pos.getPricing().getId() );
				preparedStatement.setBigDecimal( 4, pos.getPricing().getPrice() );

				preparedStatement.setBigDecimal( 5, pos.getQuantity() );
				preparedStatement.setBigDecimal( 6, pos.getCost() );
				preparedStatement.setBigDecimal( 7, pos.getTotalCost() );
				preparedStatement.setBigDecimal( 8, pos.getAmountPaid() );
				preparedStatement.setBoolean( 9, pos.isCredit() );
				preparedStatement.setBigDecimal( 10, pos.getDiscountAllowed() );
				preparedStatement.setBigDecimal( 11, customerId );
				preparedStatement.setBigDecimal( 12, pos.getChange() );
				preparedStatement.setBigDecimal( 13, pos.getBalanceToBePaidByCustomer() );
				preparedStatement.setBigDecimal( 14, mediumOfPaymentId );

				System.out.println( "pos: " + pos );

				preparedStatement.addBatch();

			}

			POS p = map.values().stream().findFirst().get();

			POS pos = new POS();

			pos.setDate( CurrentTimestamp.getDateTimeEndAtSecond() );
			pos.setItemId( p.getItemId() );
			pos.setPricing( p.getPricing() );
			pos.setPrice( BigDecimal.ZERO );
			pos.setQuantity( BigDecimal.ZERO );
			pos.setCost( p.getCost() );
			pos.setTotalCost( p.getTotalCost() );
			pos.setAmountPaid( p.getAmountPaid() );
			pos.setCredit( p.isCredit() );

			if ( p.getMediumOfPaymentId() != null ) {
				pos.setMediumOfPaymentId( p.getMediumOfPaymentId() );
				mediumOfPaymentId = pos.getMediumOfPaymentId().getId();
			} else {
				mediumOfPaymentId = BigDecimal.ZERO;
			}

			BigDecimal discount = BigDecimal.ZERO;
			if ( p.getDiscountAllowed() == null ) {
				discount = BigDecimal.ZERO;
				pos.setDiscountAllowed( discount );
			} else {
				pos.setDiscountAllowed( p.getDiscountAllowed() );
			}

			BigDecimal customerId = BigDecimal.ZERO;

			if ( p.getCustomerId() == null ) {
				customerId = BigDecimal.ZERO;
			} else {
				customerId = p.getCustomerId().getId();
			}

			pos.setCustomerId( p.getCustomerId() );
			pos.setChange( p.getChange() );
			pos.setBalanceToBePaidByCustomer( p.getBalanceToBePaidByCustomer() );

			preparedStatement.setString( 1, pos.getDate() );
			preparedStatement.setBigDecimal( 2, pos.getItemId().getId() );
			preparedStatement.setBigDecimal( 3, pos.getPricing().getId() );
			preparedStatement.setBigDecimal( 4, pos.getPricing().getPrice() );

			preparedStatement.setBigDecimal( 5, pos.getQuantity() );
			preparedStatement.setBigDecimal( 6, pos.getCost() );
			preparedStatement.setBigDecimal( 7, pos.getTotalCost() );
			preparedStatement.setBigDecimal( 8, pos.getAmountPaid() );
			preparedStatement.setBoolean( 9, pos.isCredit() );
			preparedStatement.setBigDecimal( 10, pos.getDiscountAllowed() );
			preparedStatement.setBigDecimal( 11, customerId );
			preparedStatement.setBigDecimal( 12, pos.getChange() );
			preparedStatement.setBigDecimal( 13, pos.getBalanceToBePaidByCustomer() );
			preparedStatement.setBigDecimal( 14, mediumOfPaymentId );

			System.out.println( "pos final: " + pos );

			preparedStatement.addBatch();

			preparedStatement.executeBatch();
			connection.commit();
			connection.setAutoCommit( true );

		} catch (

		SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
