package com.delains.daol.licence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.licence.Licence;
import com.delains.utilities.CurrentTimestamp;

public class LicenceDAOInsert {
	private static LinkedHashMap < String, String > insertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "date_from", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "date_to", "DATE" );
		map.put( "period", "INTEGER" );
		map.put( "rand_num_id", "INTEGER" );

		return map;

	}

	public static void newLicence( Licence licence ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( insertionDefinition(), "licence" ) );

			preparedStatement.setString( 1, licence.getFromDate() );
			preparedStatement.setString( 2, licence.getToDate() );
			preparedStatement.setBigDecimal( 3, periodDeterminant( licence.getToDate() ) );
			preparedStatement.setBigDecimal( 4, licence.getRandomNumberId().getId() );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

	public static BigDecimal periodDeterminant( String dateTo ) {
		BigDecimal period = BigDecimal.ZERO;
		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet rs = null;
		try {

			preparedStatement = connection
					.prepareStatement( "select CAST((julianday(?) - julianday('now')) as Integer) as period" );
			preparedStatement.setString( 1, dateTo );
			rs = preparedStatement.executeQuery();

			if ( rs.next() ) {
				period = rs.getBigDecimal( "period" );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, rs );
		}
		return period;

	}

	public static void main( String [ ] args ) {
}

}
