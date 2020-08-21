package com.delains.daol.licence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.dao.licence.LicenceRandomNumbers;
import com.delains.dao.utils.DBUtils;

public class RandomNumberDAOInsert {

	private static LinkedHashMap < String, String > insertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "ran_num", "INTEGER" );

		return map;
	}

	public static void newRandNum() {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		/**
		 * one month
		 */

		int times = 1000;

		char oneMonth = '1';
		for ( int i = 0; i < times; i++ ) {
			LicenceRandomNumbers.addToRandomNumber( oneMonth );
		}

		/**
		 * two month
		 */
		char twoMonth = '2';
		for ( int i = 0; i < times; i++ ) {
			LicenceRandomNumbers.addToRandomNumber( twoMonth );
		}

		/**
		 * three month
		 */
		char threeMonth = '3';
		for ( int i = 0; i < times; i++ ) {
			LicenceRandomNumbers.addToRandomNumber( threeMonth );
		}
		/**
		 * four month
		 */
		char fourMonth = '4';
		for ( int i = 0; i < times; i++ ) {
			LicenceRandomNumbers.addToRandomNumber( fourMonth );
		}

		/**
		 * five month
		 */
		char fiveMonth = '5';
		for ( int i = 0; i < times; i++ ) {
			LicenceRandomNumbers.addToRandomNumber( fiveMonth );
		}

		/**
		 * six month
		 */
		char sixMonth = '6';
		for ( int i = 0; i < times; i++ ) {
			LicenceRandomNumbers.addToRandomNumber( sixMonth );
		}

		/**
		 * seven month
		 */
		char sevenMonth = '7';
		for ( int i = 0; i < times; i++ ) {
			LicenceRandomNumbers.addToRandomNumber( sevenMonth );
		}

		/**
		 * eight month
		 */
		char eightMonth = '8';
		for ( int i = 0; i < times; i++ ) {
			LicenceRandomNumbers.addToRandomNumber( eightMonth );
		}

		/**
		 * nine month
		 */
		char nineMonth = '9';
		for ( int i = 0; i < times; i++ ) {
			LicenceRandomNumbers.addToRandomNumber( nineMonth );
		}

		try {

			connection.setAutoCommit( false );

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( insertionDefinition(), "random_number" ) );

			preparedStatement.clearBatch();

			for ( Map.Entry < BigDecimal, String > map : LicenceRandomNumbers.getNumberAndLicenceMap().entrySet() ) {
				preparedStatement.setBigDecimal( 1, map.getKey() );
				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();

			connection.commit();

			connection.setAutoCommit( true );

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
