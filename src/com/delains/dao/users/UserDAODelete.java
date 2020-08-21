package com.delains.dao.users;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.delains.dao.utils.DBUtils;

public class UserDAODelete {

	public static void deleteUser( BigDecimal id ) {
		PreparedStatement preparedStatementDeleteUser = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatementDeleteUser = connection
					.prepareStatement( DBUtils.getDeleteCommandString( "users", "WHERE id=?" ) );

			preparedStatementDeleteUser.setBigDecimal( 1, id );
			preparedStatementDeleteUser.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatementDeleteUser, null );
		}
	}

	public static void deleteDefaultUser( BigDecimal id, String email ) {
		PreparedStatement preparedStatementDeleteUser = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatementDeleteUser = connection
					.prepareStatement( DBUtils.getDeleteCommandString( "users", "WHERE id=? AND user_email=?" ) );

			preparedStatementDeleteUser.setBigDecimal( 1, id );
			preparedStatementDeleteUser.setString( 2, email );
			preparedStatementDeleteUser.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatementDeleteUser, null );
		}
	}

}
