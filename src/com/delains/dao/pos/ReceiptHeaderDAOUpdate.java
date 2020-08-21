package com.delains.dao.pos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.pos.ReceiptHeader;

public class ReceiptHeaderDAOUpdate {

	private static LinkedHashMap < String, String > mapsForUpdatingItems() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "business_name", "business_name" );
		map.put( "location", "location" );
		map.put( "contact", "contact" );

		return map;

	}

	public static void updateReceiptHeader( ReceiptHeader header, BigDecimal id ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getUpdateCommandString( "receipt_header", mapsForUpdatingItems(), "WHERE id=?" ) );

			preparedStatement.setString( 1, header.getBusinessName() );
			preparedStatement.setString( 2, header.getLocation() );
			preparedStatement.setString( 3, header.getContact() );
			preparedStatement.setBigDecimal( 3, BigDecimal.ONE );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
