package com.delains.dao.customers;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.customers.Customer;

public class CustomerDAOUpdate {

	private static LinkedHashMap < String, String > mapsForUpdatingCustomers() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "customer_email", "customer_email" );
		map.put( "customer_phone", "customer_phone" );
		map.put( "customer_name", "customer_name" );
		map.put( "date", "date" );

		return map;

	}

	public static Customer updateUser( Customer customer, BigDecimal idOfCustomer ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getUpdateCommandString( "customers", mapsForUpdatingCustomers(), "WHERE id=?" ) );

			customer.setId( idOfCustomer );

			Timestamp timestamp = Timestamp.valueOf( LocalDateTime.now() );
			customer.setDate( timestamp.toString() );

			preparedStatement.setString( 1, customer.getCustomerEmail() );
			preparedStatement.setString( 2, customer.getCustomerPhone() );
			preparedStatement.setString( 3, customer.getCustomerName() );
			preparedStatement.setString( 4, customer.getDate() );
			preparedStatement.setBigDecimal( 5, customer.getId() );

			int i = preparedStatement.executeUpdate();

			if (i > 0 ){

				BigDecimal id = BigDecimal.ZERO;
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if ( rs != null && rs.next() ) {
					id = rs.getBigDecimal( 1 );
					customer.setId(id);
				}

				if (rs != null) {
					rs.close();
				}

			}




		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
		return customer;
	}

}
