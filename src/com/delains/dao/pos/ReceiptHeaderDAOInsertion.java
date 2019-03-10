package com.delains.dao.pos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.pos.ReceiptHeader;

public class ReceiptHeaderDAOInsertion {

	private static LinkedHashMap < String, String > itemInsertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "business_name", "VARCHAR(255)" );
		map.put( "location", "VARCHAR(255)" );
		map.put( "contact", "VARCHAR(255)" );

		return map;
	}

	public static void newReceiptHeader( ReceiptHeader header ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( itemInsertionDefinition(), "receipt_header" ) );

			preparedStatement.setString( 1, header.getBusinessName() );
			preparedStatement.setString( 2, header.getLocation() );
			preparedStatement.setString( 3, header.getContact() );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
