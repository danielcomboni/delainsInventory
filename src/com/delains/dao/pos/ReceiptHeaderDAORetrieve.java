package com.delains.dao.pos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.delains.dao.utils.DBUtils;
import com.delains.model.pos.ReceiptHeader;

public class ReceiptHeaderDAORetrieve {

	public static ReceiptHeader getReceiptHeaders() {

		ReceiptHeader header = new ReceiptHeader();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getRetrievingCommandString( "receipt_header", "" ) );
			resultSet = preparedStatement.executeQuery();

			if ( resultSet.next() ) {

				BigDecimal id = resultSet.getBigDecimal( "id" );
				String contact = resultSet.getString( "contact" );
				String location = resultSet.getString( "location" );
				String bizName = resultSet.getString( "business_name" );

				ReceiptHeader header2 = new ReceiptHeader();
				header2.setContact( contact );
				header2.setId( id );
				header2.setLocation( location );
				header2.setBusinessName( bizName );

				header = header2;

			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, resultSet );
		}
		return header;
	}

}
