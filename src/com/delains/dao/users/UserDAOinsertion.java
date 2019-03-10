package com.delains.dao.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.users.User;

public class UserDAOinsertion {

	// private static String checkPass( String plainPassword, String hashedPassword
	// ) {
	//
	// if ( BCrypt.checkpw( plainPassword, hashedPassword ) )
	//
	// return "true";
	// else
	//
	// return "false";
	//
	// }

	private static LinkedHashMap < String, String > insertIntoUsersTable() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "user_name", "VARCHAR(255)" );
		map.put( "user_email", "VARCHAR(255)" );
		map.put( "user_phone", "VARCHAR(255)" );
		map.put( "user_password", "VARCHAR(255)" );
		map.put( "is_admin", "boolean" );

		return map;
	}

	public static void newUser( User user ) {

		PreparedStatement preparedStatementInsertUser = null;
		Connection connectionInsertUser = DBUtils.connect();

		try {

			preparedStatementInsertUser = connectionInsertUser
					.prepareStatement( DBUtils.getInsertCommandString( insertIntoUsersTable(), "users" ) );

			String hashPassword = BCrypting.hashPassword( user.getUserPassword() );
			System.out.println( "hashed: " + hashPassword );

			String phone = null;

			if ( user.getUserEmail().equals( "admin@admin.com" ) ) {
				phone = null;
			} else {
				if ( user.getUserPhone().contains( " " ) ) {
					phone = user.getUserPhone().replaceAll( " ", "" );
				} else {
					phone = user.getUserPhone();
				}
			}

			preparedStatementInsertUser.setString( 1, user.getUserName() );
			preparedStatementInsertUser.setString( 2, user.getUserEmail().replaceAll( " ", "" ) );
			preparedStatementInsertUser.setString( 3, phone );
			preparedStatementInsertUser.setString( 4, hashPassword );
			preparedStatementInsertUser.setBoolean( 5, user.isAdmin() );

			preparedStatementInsertUser.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connectionInsertUser, preparedStatementInsertUser, null );
		}
	}

}
