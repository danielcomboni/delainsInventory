package com.delains.daol.licence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.delains.dao.utils.DBUtils;
import com.delains.model.licence.Licence;
import com.delains.model.licence.RandomNumber;

public class LicenceDAORetrieve {

	private LicenceDAORetrieve() {

	}

	public static List < Licence > getAllLicences() {

		List < Licence > list = new ArrayList <>();

		Connection connection = DBUtils.connect();
		PreparedStatement pst = null;
		ResultSet rs = null;

		Map < BigDecimal, RandomNumber > map = RandomNumberHibernation.getMapOfRandomNumberToItsID();

		try {
			pst = connection.prepareStatement( "select * from licence" );
			rs = pst.executeQuery();
			while ( rs.next() ) {

				Licence l = new Licence();
				l.setId( rs.getBigDecimal( "id" ) );
				l.setFromDate( rs.getString( "date_from" ) );
				l.setToDate( rs.getString( "date_to" ) );
				l.setPeriod( rs.getBigDecimal( "period" ) );

				l.setRandomNumberId( map.get( rs.getBigDecimal( "rand_num_id" ) ) );

				list.add( l );

			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, pst, rs );
		}
		return list;

	}

	public static Licence getLastLicence() {
		Licence list = new Licence();
		Connection connection = DBUtils.connect();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map < BigDecimal, RandomNumber > map = RandomNumberHibernation.getMapOfRandomNumberToItsID();
		try {
			pst = connection.prepareStatement( "select * from licence order by id desc limit 1" );
			rs = pst.executeQuery();
			while ( rs.next() ) {
				Licence l = new Licence();
				l.setId( rs.getBigDecimal( "id" ) );
				l.setFromDate( rs.getString( "date_from" ) );
				l.setToDate( rs.getString( "date_to" ) );
				l.setPeriod( rs.getBigDecimal( "period" ) );
				l.setRandomNumberId( map.get( rs.getBigDecimal( "rand_num_id" ) ) );
				list = l;
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, pst, rs );
		}
		return list;
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

}
