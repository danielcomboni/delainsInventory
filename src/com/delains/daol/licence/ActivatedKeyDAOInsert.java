package com.delains.daol.licence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.licence.ActivatedKey;

public class ActivatedKeyDAOInsert {

	private ActivatedKeyDAOInsert() {

	}

	private static LinkedHashMap < String, String > insertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "licence_id", "INTEGER" );
		map.put( "key", "LONGTEXT" );

		return map;

	}

	public static void newActivatedKey( ActivatedKey key ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( insertionDefinition(), "activated_key" ) );

			preparedStatement.setBigDecimal( 1, key.getLicenceId().getId() );
			preparedStatement.setString( 2, key.getKey() );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
