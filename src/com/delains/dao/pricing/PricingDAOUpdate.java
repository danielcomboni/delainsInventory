package com.delains.dao.pricing;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.pricing.Pricing;

public class PricingDAOUpdate {

	private static LinkedHashMap < String, String > mapsForUpdatingPricing() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "price", "price" );
		map.put( "date", "date" );

		return map;

	}

	public static void updatePricing( Pricing pricing, BigDecimal idOfPricing ) {

		try {
			DBUtils.connect().close();
		} catch ( SQLException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getUpdateCommandString( "pricing", mapsForUpdatingPricing(), "WHERE id=?" ) );

			pricing.setId( idOfPricing );

			Timestamp timestamp = Timestamp.valueOf( LocalDateTime.now() );
			pricing.setDate( timestamp.toString() );

			preparedStatement.setBigDecimal( 1, pricing.getPrice() );
			preparedStatement.setString( 2, pricing.getDate() );
			preparedStatement.setBigDecimal( 3, pricing.getId() );

			System.out.println( "new price: " + pricing );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
