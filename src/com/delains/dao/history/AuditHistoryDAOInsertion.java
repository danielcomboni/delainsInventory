package com.delains.dao.history;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.history.AuditHistory;
import com.delains.utilities.CurrentTimestamp;

public class AuditHistoryDAOInsertion {
	private static LinkedHashMap < String, String > insertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "action", "longtext" );
		map.put( "user_id", "INTEGER" );

		return map;
	}

	public static void newAuditHistory( AuditHistory history ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( insertionDefinition(), "audit_history" ) );

			// Timestamp timestamp = Timestamp.valueOf( LocalDateTime.now() );

			history.setDate( CurrentTimestamp.getDateTimeEndAtSecond() );

			BigDecimal userId = BigDecimal.ZERO;

			if ( history.getUserid() != null ) {
				userId = history.getUserid().getId();
			}

			preparedStatement.setString( 1, history.getDate() );
			preparedStatement.setString( 2, history.getAction() );
			preparedStatement.setBigDecimal( 3, userId );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
