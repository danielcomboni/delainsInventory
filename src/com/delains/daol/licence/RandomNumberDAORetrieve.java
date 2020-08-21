package com.delains.daol.licence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.licence.RandomNumber;

public class RandomNumberDAORetrieve {

	public static List < RandomNumber > getAllRandomNumberList() {
		List < RandomNumber > randomNumbers = new ArrayList <>();
		Connection connection = DBUtils.connect();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement( "select * from random_number" );
			rs = pst.executeQuery();
			while ( rs.next() ) {
				RandomNumber rn = new RandomNumber();
				rn.setId( rs.getBigDecimal( "id" ) );
				rn.setRandNum( rs.getBigDecimal( "ran_num" ) );
				randomNumbers.add( rn );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, pst, rs );
		}
		return randomNumbers;
	}

	public static RandomNumber getARandomNumberBasingOnPeriodChosen( BigDecimal period ) {
		RandomNumber rn = new RandomNumber();
		PreparedStatement pst = null;
		Connection connection = DBUtils.connect();
		ResultSet rs = null;
		try {
			pst = connection
					.prepareStatement( "SELECT * from random_number WHERE ran_num like ? ORDER by id DESC LIMIT 1" );
			pst.setString( 1, period + "%" );
			rs = pst.executeQuery();
			while ( rs.next() ) {
				rn.setId( rs.getBigDecimal( "id" ) );
				rn.setRandNum( rs.getBigDecimal( "ran_num" ) );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, pst, rs );
		}
		return rn;
	}

	public static void deleteTheUsedRow( BigDecimal id ) {
		PreparedStatement pst = null;
		Connection connection = DBUtils.connect();
		try {
			pst = connection.prepareStatement( "DELETE FROM random_number WHERE id = ? " );
			pst.setBigDecimal( 1, id );

			pst.executeUpdate();

		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, pst, null );
		}

	}

}
